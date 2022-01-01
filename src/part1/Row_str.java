package part1;

public class Row_str {
    public String Area, Perimeter, Compactness, Kernel_lenght, Kernel_width,
            Asymmetry_coefficient, Kernel_groove_groove, Classe;

    String [] instance;


    public Row_str(double[] instance) {

        this.instance = new String[instance.length];

        for(int i=0; i<instance.length; i++){
            this.instance[i] = Double.toString(instance[i]);
        }

        init();
    }

    public Row_str(String[] instance) {
        this.instance = instance;
        init();
    }

    private void init(){
        Area = instance[0];
        Perimeter = instance[1];
        Compactness = instance[2];
        Kernel_lenght = instance[3];
        Kernel_width = instance[4];
        Asymmetry_coefficient = instance[5];
        Kernel_groove_groove = instance[6];
        Classe = instance[7];
    }

    public String[] getInstance() {
        return instance;
    }


    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getPerimeter() {
        return Perimeter;
    }

    public void setPerimeter(String perimeter) {
        Perimeter = perimeter;
    }

    public String getCompactness() {
        return Compactness;
    }

    public void setCompactness(String compactness) {
        Compactness = compactness;
    }

    public String getKernel_lenght() {
        return Kernel_lenght;
    }

    public void setKernel_lenght(String kernel_lenght) {
        Kernel_lenght = kernel_lenght;
    }

    public String getKernel_width() {
        return Kernel_width;
    }

    public void setKernel_width(String kernel_width) {
        Kernel_width = kernel_width;
    }

    public String getAsymmetry_coefficient() {
        return Asymmetry_coefficient;
    }

    public void setAsymmetry_coefficient(String asymmetry_coefficient) {
        Asymmetry_coefficient = asymmetry_coefficient;
    }

    public String getKernel_groove_groove() {
        return Kernel_groove_groove;
    }

    public void setKernel_groove_groove(String kernel_groove_groove) {
        Kernel_groove_groove = kernel_groove_groove;
    }

    public String getClasse() {
        return Classe;
    }

    public void setClasse(String classe) {
        Classe = classe;
    }

    public void setInstance(String[] instance) {
        this.instance = instance;
    }
}

