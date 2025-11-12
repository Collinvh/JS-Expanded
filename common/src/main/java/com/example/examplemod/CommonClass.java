package com.example.examplemod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import travelers.TravelersMain;

public class CommonClass {
	
	public static final String MOD_ID = "examplemod";
	public static final String MOD_NAME = "ExampleMod";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
	
    public static void init() {
        TravelersMain.registerMod(MOD_ID);
        TravelersMain.enableDebugMode();
    }
}