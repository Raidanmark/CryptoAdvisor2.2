package bot.model.entities;

public class Signal {
    private boolean signal;
    private MethodSet methodSet;
    private Long id;

    public boolean isSignal() {
        return signal;
    }

    public void setSignal(boolean signal) {
        this.signal = signal;
    }

    public MethodSet getMethodSet() {
        return methodSet;
    }

    public void setMethodSet(MethodSet methodSet) {
        this.methodSet = methodSet;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
