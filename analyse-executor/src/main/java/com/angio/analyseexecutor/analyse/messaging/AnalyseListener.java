package com.angio.analyseexecutor.analyse.messaging;

import com.angio.analyseexecutor.analyse.dto.AnalyseDto;
import com.angio.analyseexecutor.analyse.service.AnalyseExecutorService;
import com.mathworks.toolbox.javabuilder.MWException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@AllArgsConstructor
@Component
public class AnalyseListener {

    private final AnalyseExecutorService analyseExecutorService;
    private final AnalyseSender analyseSender;

    @JmsListener(destination = "${analyseexecutor.app.jms.analyse-to-execute-queue}")
    public void receiveAnalyseToExecute(AnalyseDto analyse) throws IOException, MWException, SQLException {

        log.trace("receiveAnalyseToExecute() - start, analyse received to execute: " + analyse);

        log.trace("receiveAnalyseToExecute() - execute analyse");
        analyseExecutorService.executeAnalyse(analyse);

        log.trace("receiveAnalyseToExecute() - send result: ", analyse);
        analyseSender.sendAnalyseResult(analyse);

        log.trace("receiveAnalyseToExecute() - end");
    }
}
