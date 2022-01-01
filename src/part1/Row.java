package part1;

public class Row {


    public Double Area, Perimeter, Compactness, Kernel_lenght, Kernel_width,
            Asymmetry_coefficient, Kernel_groove_groove, Classe;

    double [] instance;

    public Row(double[] instance) {
        this.instance = instance;

        Area = instance[0];
        Perimeter = instance[1];
        Compactness = instance[2];
        Kernel_lenght = instance[3];
        Kernel_width = instance[4];
        Asymmetry_coefficient = instance[5];
        Kernel_groove_groove = instance[6];
        Classe = instance[7];

    }

    public double[] getInstance() {
        return instance;
    }

    public Double getArea() {
        return Area;
    }

    public void setArea(Double area) {
        Area = area;
    }

    public Double getPerimeter() {
        return Perimeter;
    }

    public void setPerimeter(Double perimeter) {
        Perimeter = perimeter;
    }

    public Double getCompactness() {
        return Compactness;
    }

    public void setCompactness(Double compactness) {
        Compactness = compactness;
    }

    public Double getKernel_lenght() {
        return Kernel_lenght;
    }

    public void setKernel_lenght(Double kernel_lenght) {
        Kernel_lenght = kernel_lenght;
    }

    public Double getKernel_width() {
        return Kernel_width;
    }

    public void setKernel_width(Double kernel_width) {
        Kernel_width = kernel_width;
    }

    public Double getAsymmetry_coefficient() {
        return Asymmetry_coefficient;
    }

    public void setAsymmetry_coefficient(Double asymmetry_coefficient) {
        Asymmetry_coefficient = asymmetry_coefficient;
    }

    public Double getKernel_groove_groove() {
        return Kernel_groove_groove;
    }

    public void setKernel_groove_groove(Double kernel_groove_groove) {
        Kernel_groove_groove = kernel_groove_groove;
    }

    public Double getClasse() {
        return Classe;
    }

    public void setClasse(Double classe) {
        Classe = classe;
    }

    public void setInstance(double[] instance) {
        this.instance = instance;
    }
}
