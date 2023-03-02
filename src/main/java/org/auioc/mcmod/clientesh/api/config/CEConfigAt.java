package org.auioc.mcmod.clientesh.api.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface CEConfigAt {

    public Type type();

    public String path() default "";

    //=============================================================================================================== //

    public static enum Type {

        TWEAKS("tweaks"),
        WIDGETS("widgets"),
        HUD("hud");

        private final String path;

        private Type(String path) { this.path = path; }

        public String getPath() { return path; }

    }

}
