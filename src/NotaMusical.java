
public class NotaMusical {

    private String nota;
    private String figura;
    private String octava;

    public NotaMusical(){
        
    }

    public NotaMusical(String nota, String figura, String octava) {
        this.nota = nota;
        this.figura = figura;
        this.octava = octava;
    }
    
    public NotaMusical siguiente;

    public String getNota() {
        return nota;
    }
    public void setNota(String nota) {
        this.nota = nota;
    }
    public String getFigura() {
        return figura;
    }
    public void setFigura(String figura) {
        this.figura = figura;
    }
    public String getOctava() {
        return octava;
    }
    public void setOctava(String octava) {
        this.octava = octava;
    }


}
