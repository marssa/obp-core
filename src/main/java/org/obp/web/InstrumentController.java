package org.obp.web;

import org.obp.Instrument;
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
        List<Instrument> instruments = new ArrayList<>(obp.getInstruments());
        Collections.sort(instruments, new Comparator<Instrument>() {

            private String orderStr(Instrument instrument) {
                String name = instrument.getName();
                switch(instrument.getStatus()) {
                    case OPERATIONAL: return "0"+name;
                    case PAUSED: return "0"+name;
                    default: return "9"+name;
                }
            }

            @Override
            public int compare(Instrument o1, Instrument o2) {
                return orderStr(o1).compareTo(orderStr(o2));
            }

        });
        model.addAttribute("instruments",instruments);
        return "simple/instruments";
    }

    @RequestMapping("/secure/instrument/pause")
    public ResponseEntity pause(@RequestParam String id) {
        Instrument instrument = obp.getInstrument(id);
        if(instrument==null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else if(instrument.getStatus()!= Instrument.Status.OPERATIONAL) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        instrument.pause();
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping("/secure/instrument/resume")
    public ResponseEntity resume(@RequestParam String id) {
        Instrument instrument = obp.getInstrument(id);
        if(instrument==null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else if(instrument.getStatus()!= Instrument.Status.PAUSED) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        instrument.resume();
        return new ResponseEntity(HttpStatus.OK);
    }
}
