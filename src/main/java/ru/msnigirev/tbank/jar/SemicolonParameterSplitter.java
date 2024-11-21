package ru.msnigirev.tbank.jar;

import com.beust.jcommander.converters.IParameterSplitter;

import java.util.Arrays;
import java.util.List;

public class SemicolonParameterSplitter implements IParameterSplitter {

    public List<String> split(String value) {
        return Arrays.asList(value.split(";"));
    }

}