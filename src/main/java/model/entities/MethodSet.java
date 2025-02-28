package model.entities;

import java.util.ArrayList;
import java.util.List;

public class MethodSet {
    private List<String> parameters = new ArrayList<>();
    private Method method;
    private Signal signal;
    private Long id;

    public Signal getSignal() {
        return signal;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    public Method getMethod() {
        return method;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
