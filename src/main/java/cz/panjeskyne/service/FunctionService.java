package cz.panjeskyne.service;


import cz.panjeskyne.model.Function;
import cz.panjeskyne.service.impl.FunctionServiceImpl;

public interface FunctionService {
	
	static Function getFunction(String identifier) {
		return FunctionServiceImpl.getFunction(identifier); // TODO
	}
}
