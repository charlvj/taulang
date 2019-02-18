/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.AbstractRegister;
import com.charlware.taulang.language.ErrorFactory;
import com.charlware.taulang.language.TauError;
import com.charlware.taulang.values.BooleanValue;
import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.NumberValue;
import com.charlware.taulang.values.StringValue;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class ErrorFunctionRegister extends AbstractRegister {

    @Override
    public void registerAll() {
        reg(new GenericFunction1("throwError", "msg") {
            @Override
            public Value execute(Value msg) throws Exception {
                return new ErrorValue(ErrorFactory.createError(msg.asString()));
            }
        });
        
        reg(new GenericFunction1("isError", "error") {
            @Override
            public Value execute(Value error) throws Exception {
                return BooleanValue.valueOf(error instanceof ErrorValue);
            }
        });
        
        reg(new GenericFunction1("error.message", "error") {
            @Override
            public Value execute(Value error) throws Exception {
                return new StringValue(getError(error).getMessage());
            }
        });
        
        reg(new GenericFunction1("error.code", "error") {
            @Override
            public Value execute(Value error) throws Exception {
                return new NumberValue(getError(error).getCode());
            }
        });
        
        reg(new GenericFunction1("error.token", "error") {
            @Override
            public Value execute(Value error) throws Exception {
                return new StringValue(getError(error).getToken().getSource());
            }
        });
    }
    
    protected TauError getError(Value error) throws Exception {
        return ((ErrorValue) error).getValue();
    }
}
