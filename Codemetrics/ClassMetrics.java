package codemetrics;

public class ClassMetrics {
    private String      name;
    private String      simpleName;
    private int         nrMPublic;
    private int         nrMPrivate;
    private int         nrMProtected;
    private int         nrInterfaces;
    private int         derivationDepth;
    private double      averageParameters;

    public ClassMetrics(String name, String simpleName, int nrMPublic, int nrMPrivate, int nrMProtected, int nrInterfaces, int derivationDepth, double averageParameters) {
        this.name = name;
        this.simpleName = simpleName;
        this.nrMPublic = nrMPublic;
        this.nrMPrivate = nrMPrivate;
        this.nrMProtected = nrMProtected;
        this.nrInterfaces = nrInterfaces;
        this.derivationDepth = derivationDepth;
        this.averageParameters = averageParameters;
    }

    //getter and setter

    public int getNrMPublic() {
        return nrMPublic;
    }

    public void setNrMPublic(int nrMPublic) {
        this.nrMPublic = nrMPublic;
    }

    public int getNrMPrivate() {
        return nrMPrivate;
    }

    public void setNrMPrivate(int nrMPrivate) {
        this.nrMPrivate = nrMPrivate;
    }

    public int getNrMProtected() {
        return nrMProtected;
    }

    public void setNrMProtected(int nrMProtected) {
        this.nrMProtected = nrMProtected;
    }

    public int getNrInterfaces() {
        return nrInterfaces;
    }

    public void setNrInterfaces(int nrInterfaces) {
        this.nrInterfaces = nrInterfaces;
    }

    public int getDerivationDepth() {
        return derivationDepth;
    }

    public void setDerivationDepth(int derivationDepth) {
        this.derivationDepth = derivationDepth;
    }

    public double getAverageParameters() {
        return averageParameters;
    }

    public void setAverageParameters(double averageParameters) {
        this.averageParameters = averageParameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }
}
