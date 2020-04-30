/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.AbstractRegister;
import com.charlware.taulang.MemoryScope;
import com.charlware.taulang.language.TauObject;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.ObjectValue;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class ObjectFunctionsRegister extends AbstractRegister {

    @Override
    public void registerAll() {
        reg(new GenericFunction3("define_object", "object_name", "params", "init") {
            @Override
            public Value execute(Value objectName, Value params, Value init) throws Exception {
                String sObjectName = objectName.asString();
                ListValue initCodeList = (ListValue) init;
                
                TauObject tauObject = new TauObject();
                ObjectValue val = new ObjectValue(tauObject);
                MemoryScope definedScope = runtime.getMemory().getCurrentScope();
                definedScope.put(new ValueFunction(objectName.asString(), val));
                MemoryScope objectScope = runtime.getMemory().pushScope();
                try {
                    tauObject.setScope(objectScope);
                    AnonymousFunction initFunc = new AnonymousFunction(new String[] {}, initCodeList);
                    initFunc.setRuntime(runtime);
                    initFunc.setMemory(objectScope);
                    initFunc.execute();
                } finally {
                    runtime.getMemory().popScope();
                }
                
                return val;
            }
        });
    }
    
}
