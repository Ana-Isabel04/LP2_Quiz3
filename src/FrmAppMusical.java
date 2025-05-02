
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.core.type.TypeReference;

public class FrmAppMusical extends JFrame {

    File archivoSeleccionado = new File("");
    String[] columnas = { "Nota", "Figura", "Octava" };
    String[] notas = { "NOTA", "DO", "RE", "MI", "FA", "SOL", "LA", "SI" };
    String[] figuras = { "FIGURA", "REDONDA", "BLANCA", "NEGRA", "CORCHEA", "SEMICORCHEA", "FUSA", "SEMIFUSA" };
    String[] octavas = { "OCTAVA", "1", "2", "3", "4", "5", "6", "7", "8" };
    String nombreArchivo = "";
    DefaultTableModel model = new DefaultTableModel(columnas, 0);
    JTable tabla = new JTable(model);
    int fila = -1;
    ListaNotas agregador = new ListaNotas();

    
    JComboBox jnota = new JComboBox<>(notas);
    JComboBox jFigura = new JComboBox<>(figuras);
    JComboBox jOctava = new JComboBox<>(octavas);

    public FrmAppMusical() {
        setSize(600, 300);
        setTitle("Editor de melodías");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);



        
        JButton btnArchivo = new JButton("Abrir archivo Json");
        btnArchivo.setIcon(new ImageIcon(getClass().getResource("/img/carpeta.png")));
        btnArchivo.setBounds(0, 0, 40, 40);
        getContentPane().add(btnArchivo);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setIcon(new ImageIcon(getClass().getResource("/img/disco-flexible.png")));
        btnGuardar.setBounds(40, 0, 45, 40);
        getContentPane().add(btnGuardar);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setIcon(new ImageIcon(getClass().getResource("/img/nota-musical.png")));
        btnAgregar.setBounds(80, 0, 45, 40);
        getContentPane().add(btnAgregar);

        JButton btnEditar = new JButton("Editar fila");
        btnEditar.setIcon(new ImageIcon(getClass().getResource("/img/editar.png")));
        btnEditar.setBounds(425, 0, 45, 40);
        getContentPane().add(btnEditar);

        JButton btnEliminar = new JButton("Eliminar fila");
        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/img/borrar.png")));
        btnEliminar.setBounds(470, 0, 45, 40);
        getContentPane().add(btnEliminar);

        JButton btnReproducir = new JButton("Reproducir fila");
        btnReproducir.setIcon(new ImageIcon(getClass().getResource("/img/boton-de-play.png")));
        btnReproducir.setBounds(515, 0, 45, 40);
        getContentPane().add(btnReproducir);

        btnArchivo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cargar();
            }

        });
        

        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                guardar();
            }
        });


        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Agregar();

            }
        });

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarNota();

            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                eliminarFila();
            }
        });


        btnReproducir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                reproducirNotas();
            }
        });

        tabla.getSelectionModel().addListSelectionListener(event -> {
            fila = tabla.getSelectedRow();
            NotaMusical notaMusical = new NotaMusical(
                    tabla.getValueAt(fila, 0).toString(),
                    tabla.getValueAt(fila, 1).toString(),
                    tabla.getValueAt(fila, 2).toString());

            jnota.setSelectedItem(notaMusical.getNota());
            jFigura.setSelectedItem(notaMusical.getFigura());
            jOctava.setSelectedItem(notaMusical.getOctava());
        });

        jnota.setBounds(125, 0, 100, 40);
        jFigura.setBounds(225, 0, 100, 40);
        jOctava.setBounds(325, 0, 100, 40);
        getContentPane().add(jnota);
        getContentPane().add(jFigura);
        getContentPane().add(jOctava);

        JScrollPane panel = new JScrollPane(tabla);

        panel.setBounds(50, 50, 500, 200);
        getContentPane().add(panel);

    }


    private void Agregar() {
        if (!"NOTA".equals(jnota.getSelectedItem()) && !"FIGURA".equals(jFigura.getSelectedItem()) && !"OCTAVA".equals(jOctava.getSelectedItem())) {
            NotaMusical nuevaNota = new NotaMusical((String) jnota.getSelectedItem(),(String) jFigura.getSelectedItem(), (String) jOctava.getSelectedItem());
            agregador.agregarNota(nuevaNota);
            model.addRow(new Object[] { nuevaNota.getNota(), nuevaNota.getFigura(), nuevaNota.getOctava() });
            System.out.println(nuevaNota.getNota()+ nuevaNota.getFigura()+ nuevaNota.getOctava());
        }else {
            JOptionPane.showMessageDialog(null, "Debes seleccionar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarNota(){
        if (fila != -1 && !"NOTA".equals(jnota.getSelectedItem()) && !"FIGURA".equals(jFigura.getSelectedItem()) && !"OCTAVA".equals(jOctava.getSelectedItem())){
            NotaMusical nuevaNota = new NotaMusical((String) jnota.getSelectedItem(),(String) jFigura.getSelectedItem(), (String) jOctava.getSelectedItem());
            
            agregador.actualizarPorIndice(fila, nuevaNota);
            model.setValueAt(nuevaNota.getNota(), fila, 0);
            model.setValueAt(nuevaNota.getFigura(), fila, 1);
            model.setValueAt(nuevaNota.getOctava(), fila, 2);
        }else{
            JOptionPane.showMessageDialog(null, "No se puede editar, por favor selecciona una fila", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarFila(){
        if (fila != -1) {
            agregador.eliminarPorIndice(fila);
            model.removeRow(fila);
        }else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila para eliminar", "Error en la selección de fila", JOptionPane.ERROR_MESSAGE);
        }
    }

    List<NotaMusical> lista = new ArrayList<>();

        private void cargar() {
        nombreArchivo = Archivo.elegirArchivo();
        if (!nombreArchivo.equals("")) {
            lista = Archivo.leerJson(nombreArchivo,
                    new TypeReference<List<NotaMusical>>() {});
            if (lista != null) {
                agregador = new ListaNotas();
                model.setRowCount(0);
                for (NotaMusical nota : lista) {
                    agregador.agregarNota(nota);
                    model.addRow(new Object[]{nota.getNota(), nota.getFigura(), nota.getOctava()});
                }
                JOptionPane.showMessageDialog(null, "Archivo cargado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo leer el archivo JSON");
            }
        }
    }

    private void guardar() {
        nombreArchivo = Archivo.elegirArchivo();
        if (!nombreArchivo.equals("")) {
            List<NotaMusical> listaNotas = new ArrayList<>();
            for (int i = 0; i < model.getRowCount(); i++) {
                String nota = (String) model.getValueAt(i, 0);
                String figura = (String) model.getValueAt(i, 1);
                String octava = (String) model.getValueAt(i, 2);
                listaNotas.add(new NotaMusical(nota, figura, octava));
            }
            if (Archivo.guardarJson(nombreArchivo, listaNotas)) {
                JOptionPane.showMessageDialog(null, "Archivo guardado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo guardar el archivo JSON");
            }
        }
    }

    private void reproducirNotas(){
        ReproductorAudioMIDI.reproducirTodasLasNotas(lista);
    }
}
