package com.imangazalievm.bubbble.domain.commons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public abstract class TwoDirectionMapper<From, To> {

    public abstract To direct(From entity);
    public abstract From indirect(To entity);

    public List<To> direct(Collection<From> entities) {
        final List<To> result = new ArrayList<>(entities.size());
        for (From from : entities) {
            result.add(direct(from));
        }
        return result;
    }

    public List<From> indirect(Collection<To> entities) {
        final List<From> result = new ArrayList<>(entities.size());
        for (To from : entities) {
            result.add(indirect(from));
        }
        return result;
    }

}