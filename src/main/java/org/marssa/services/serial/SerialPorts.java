package org.marssa.services.serial;

import gnu.io.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */

@Service
public class SerialPorts {
    private static Logger logger = Logger.getLogger(SerialPorts.class);

    public static final int COMM_BUFFER_SIZE = 1024;

    private Map<String, RegistryEntry> registry = new HashMap<>();
    private Map<String, AllocationEntry> allocated = new HashMap<>();

    @PostConstruct
    void init() {

        // TODO: populate from some sane configuration file with uniqueness check
        registry.put("bu353-port",new RegistryEntry("/dev/cu.usbserial",4800,8,false,1));

    }

    @PreDestroy
    public void releaseAll() {
        logger.info("shutting down, releasing "+allocated.size()+" ports ...");
        for(String name : allocated.keySet()) {
            logger.info("release "+name+" ...");
            release(name);
        }
        logger.info("done.");
    }

    public synchronized SerialPort open(String name, String requestorId) throws Exception {
        RegistryEntry regEntry = registry.get(name);
        if(regEntry==null) {
            throw new IllegalArgumentException("requested serial port "+name+" is not available");
        }

        AllocationEntry allocEntry = allocated.get(name);
        if(allocEntry != null) {
            throw new IllegalArgumentException("port "+name+" is already allocated by "+allocEntry.getRequestorId());
        }

        SerialPort port = createPort(regEntry);
        allocated.put(name, new AllocationEntry(port, requestorId));
        return port;
    }

    private SerialPort createPort(RegistryEntry entry) throws Exception {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(entry.getPortName());
        if (portIdentifier.isCurrentlyOwned()) {
            throw new IllegalAccessError("port "+entry.getPortName()+" is currently in use");
        }

        SerialPort port = (SerialPort)portIdentifier.open(this.getClass().getName(),2000);
        port.setSerialPortParams(entry.getBaudRate(),entry.getDataBits(),entry.getStopBits(),entry.getParityAsInt());
        return port;
    }

    public synchronized void release(String name) {
        AllocationEntry allocEntry = allocated.get(name);
        if(allocEntry==null) {
            throw new IllegalArgumentException("port "+name+" hasn't been allocated");
        }

        allocEntry.getPort().close();
        allocated.remove(name);
    }

    public boolean isAvailable(String name) {
        return registry.containsKey(name) && !allocated.containsKey(name);
    }
}
