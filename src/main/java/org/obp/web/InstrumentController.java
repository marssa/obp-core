package org.obp.web;

import org.obp.Device;
import org.obp.local.LocalObpInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Robert Jaremczak
 * Date: 2014-5-19
 */
@Controller
public class InstrumentController {

    @Autowired
    private LocalObpInstance obp;

    @RequestMapping("/simple/instruments")
    public String instruments(ModelMap model) {
        List<Device> devices = new ArrayList<>(obp.getInstruments());
        Collections.sort(devices, new Comparator<Device>() {

            private String orderStr(Device device) {
                String name = device.getName();
                switch(device.getStatus()) {
                    case OPERATIONAL: return "0"+name;
                    case PAUSED: return "0"+name;
                    default: return "9"+name;
                }
            }

            @Override
            public int compare(Device o1, Device o2) {
                return orderStr(o1).compareTo(orderStr(o2));
            }

        });
        model.addAttribute("instruments", devices);
        return "simple/instruments";
    }

    @RequestMapping("/secure/instrument/pause")
    public ResponseEntity pause(@RequestParam String id) {
        Device device = obp.getInstrument(id);
        if(device ==null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else if(device.getStatus()!= Device.Status.OPERATIONAL) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        device.pause();
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping("/secure/instrument/resume")
    public ResponseEntity resume(@RequestParam String id) {
        Device device = obp.getInstrument(id);
        if(device ==null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else if(device.getStatus()!= Device.Status.PAUSED) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        device.resume();
        return new ResponseEntity(HttpStatus.OK);
    }
}
