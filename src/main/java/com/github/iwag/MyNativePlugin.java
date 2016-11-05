package com.github.iwag;

import java.util.List;
import java.util.Map;

import org.elasticsearch.ElasticsearchIllegalArgumentException;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.xcontent.support.XContentMapValues;
import org.elasticsearch.index.fielddata.ScriptDocValues;
import org.elasticsearch.nativescript.longarraycount.LongArrayBinaryBoundSearch;
import org.elasticsearch.plugins.AbstractPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.script.AbstractLongSearchScript;
import org.elasticsearch.script.ExecutableScript;
import org.elasticsearch.script.NativeScriptFactory;
import org.elasticsearch.script.*;

/**
 * In addtion, add following setting to es-plugins.properties
 * plugin=org.elasticsearch.plugins.custom.MyNativePlugin
 */
public class MyNativePlugin extends AbstractPlugin {
    @Override
    public String name() {
        return "my-native-plugin script";
    }

    @Override
    public String description() {
        return "this plugin is great something";
    }

    public void onModule(ScriptModule scriptModule) {
        scriptModule.registerScript("my_script", MyNativeScriptFactory.class);
    }

    public static class MyNativeScriptFactory implements NativeScriptFactory {
        @Override
        public ExecutableScript newScript(@Nullable Map<String, Object> params) {
            return new MyNativeScript();
        }
    }

    public static class MyNativeScript extends AbstractLongSearchScript {

        public MyNativeScript() {
        }


        @Override
        public long runAsLong() {
            ScriptDocValues values = (ScriptDocValues) doc().get(field);
            if(values.isEmpty()) return 0L;
            ((ScriptDocValues.Longs) values).getValues().get(0);
        }
    }
}

