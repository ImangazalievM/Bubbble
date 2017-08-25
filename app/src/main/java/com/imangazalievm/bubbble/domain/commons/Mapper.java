package com.imangazalievm.bubbble.domain.commons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public abstract class Mapper<From, To> {

    public abstract To map(From entity);

    public List<To> map(Collection<From> entities) {
        final List<To> result = new ArrayList<>(entities.size());
        for (From from : entities) {
            result.add(map(from));
        }
        return result;
    }

}