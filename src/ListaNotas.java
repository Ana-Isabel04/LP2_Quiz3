public class ListaNotas {

    private NotaMusical cabeza;

    public ListaNotas() {
        cabeza = null;
    }

    public void agregarNota(NotaMusical nuevaNota) {
        if (cabeza == null) {
            cabeza = nuevaNota;
        } else {
            NotaMusical actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevaNota;
        }
    }

    public boolean actualizarPorIndice(int indice, NotaMusical nuevaNota) {
        NotaMusical actual = cabeza;
        int i = 0;

        while (actual != null) {
            if (i == indice) {
                actual.setNota(nuevaNota.getNota());
                actual.setFigura(nuevaNota.getFigura());
                actual.setOctava(nuevaNota.getOctava());
                return true;
            }
            actual = actual.siguiente;
            i++;
        }
        return false;
    }

    public boolean eliminarPorIndice(int indice) {
        NotaMusical actual = cabeza;
        NotaMusical anterior = null;
        int i = 0;

        while (actual != null) {
            if (i == indice) {
                if (anterior == null) {
                    cabeza = actual.siguiente;
                } else {
                    anterior.siguiente = actual.siguiente;
                }
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
            i++;
        }

        return false;
    }

}