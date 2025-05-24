package br.com.sh.apiexample.populator;

public interface Populator<SOURCE, TARGET> {

    /**
     * used for dto to model
     */
    TARGET populate(SOURCE source, TARGET target);

    /**
     * used for dto to model
     */
    TARGET populate(SOURCE source);


}
