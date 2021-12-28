package part1;


import javafx.scene.control.TextField;

public class Row_Field {


    public TextField Area, Perimeter, Compactness, Kernel_lenght, Kernel_width,
            Asymmetry_coefficient, Kernel_groove_groove, Classe;

    private double [] instance;


    public Row_Field() {

        Area = new TextField("");
        Perimeter = new TextField("");
        Compactness = new TextField("");
        Kernel_lenght = new TextField("");
        Kernel_width = new TextField("");
        Asymmetry_coefficient = new TextField("");
        Kernel_groove_groove = new TextField("");
        Classe = new TextField("");
    }

    public Row_Field(double[] instance) {

        this.instance = instance;
        Area = new TextField(Double.toString(instance[0]));
        Perimeter = new TextField(Double.toString(instance[1]));
        Compactness = new TextField(Double.toString(instance[2]));
        Kernel_lenght = new TextField(Double.toString(instance[3]));
        Kernel_width = new TextField(Double.toString(instance[4]));
        Asymmetry_coefficient = new TextField(Double.toString(instance[5]));
        Kernel_groove_groove = new TextField(Double.toString(instance[6]));
        Classe = new TextField(Double.toString(instance[7]));

    }

    public double[] get_doubles(){

        double [] doubles = new double[8];

        doubles[0] = Double.parseDouble(Area.getText());
        doubles[1] = Double.parseDouble(Perimeter.getText());
        doubles[2] = Double.parseDouble(Compactness.getText());
        doubles[3] = Double.parseDouble(Kernel_lenght.getText());
        doubles[4] = Double.parseDouble(Kernel_width.getText());
        doubles[5] = Double.parseDouble(Asymmetry_coefficient.getText());
        doubles[6] = Double.parseDouble(Kernel_groove_groove.getText());
        doubles[7] = Double.parseDouble(Classe.getText());

        this.instance = doubles;

        return doubles;
    }

    public double[] getInstance() {
        return instance;
    }

    public TextField getArea() {
        return Area;
    }

    public void setArea(TextField area) {
        Area = area;
    }

    public TextField getPerimeter() {
        return Perimeter;
    }

    public void setPerimeter(TextField perimeter) {
        Perimeter = perimeter;
    }

    public TextField getCompactness() {
        return Compactness;
    }

    public void setCompactness(TextField compactness) {
        Compactness = compactness;
    }

    public TextField getKernel_lenght() {
        return Kernel_lenght;
    }

    public void setKernel_lenght(TextField kernel_lenght) {
        Kernel_lenght = kernel_lenght;
    }

    public TextField getKernel_width() {
        return Kernel_width;
    }

    public void setKernel_width(TextField kernel_width) {
        Kernel_width = kernel_width;
    }

    public TextField getAsymmetry_coefficient() {
        return Asymmetry_coefficient;
    }

    public void setAsymmetry_coefficient(TextField asymmetry_coefficient) {
        Asymmetry_coefficient = asymmetry_coefficient;
    }

    public TextField getKernel_groove_groove() {
        return Kernel_groove_groove;
    }

    public void setKernel_groove_groove(TextField kernel_groove_groove) {
        Kernel_groove_groove = kernel_groove_groove;
    }

    public TextField getClasse() {
        return Classe;
    }

    public void setClasse(TextField classe) {
        Classe = classe;
    }
}
