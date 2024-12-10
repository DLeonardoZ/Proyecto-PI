package UIControles;

import Logic.Hilos.HiloCliente;
import Logic.Hilos.HiloServidor;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;

public class ClienteServidor extends JPanel {
    static JButton btnParalela;
    static JLabel lblEstado;
    static JTextField serverAddress;
    static JButton btnStopC;
    JButton btnStop;
    JButton btnServidor;
    JButton btnCliente;

    Boolean puertoAbierto = false;
    String ip;

    public ClienteServidor() {
        setSize(350, 140);
        setBackground(Color.WHITE);
        setLayout(null);

        btnCliente = new JButton("Modo Cliente");
        btnCliente.setFont(btnCliente.getFont().deriveFont(16.0f));
        btnCliente.setSize(150, 30);
        btnCliente.setLocation(20, 10);

        btnStop = new JButton("Detener");
        btnStop.setHorizontalAlignment(JTextField.CENTER);
        btnStop.setFont(btnStop.getFont().deriveFont(16.0f));
        btnStop.setEnabled(false);
        btnStop.setSize(150, 30);
        btnStop.setLocation(180, 50);

        btnStopC = new JButton("Detener Cliente");
        btnStopC.setHorizontalAlignment(JTextField.CENTER);
        btnStopC.setFont(btnStop.getFont().deriveFont(16.0f));
        btnStopC.setVisible(false);
        btnStopC.setEnabled(false);
        btnStopC.setSize(150, 30);
        btnStopC.setLocation(180, 50);

        btnServidor = new JButton("Modo Servidor");
        btnServidor.setFont(btnServidor.getFont().deriveFont(16.0f));
        btnServidor.setSize(150, 30);
        btnServidor.setLocation(180, 10);

        serverAddress = new JTextField("192.168.100.0");
        serverAddress.setHorizontalAlignment(JTextField.CENTER);
        serverAddress.setFont(serverAddress.getFont().deriveFont(16.0f));
        serverAddress.setSize(150, 30);
        serverAddress.setLocation(20, 50);

        btnParalela = new JButton("Paralela");
        btnParalela.setFont(btnParalela.getFont().deriveFont(16.0f));
        btnParalela.setEnabled(false);
        btnParalela.setSize(150, 30);
        btnParalela.setLocation(20, 90);

        lblEstado = new JLabel("Desconectado");
        lblEstado.setFont(btnParalela.getFont().deriveFont(16.0f));
        lblEstado.setHorizontalAlignment(JLabel.CENTER);
        lblEstado.setSize(150, 30);
        lblEstado.setLocation(180, 90);

        add(btnServidor);
        add(btnCliente);
        add(btnStop);
        add(btnStopC);
        add(serverAddress);
        add(btnParalela);
        add(lblEstado);

        btnCliente.addActionListener(e -> {
            ip = serverAddress.getText();
            if (validarAddress(ip)) {
                serverAddress.setEditable(false);
                btnServidor.setEnabled(false);
                btnStop.setVisible(false);
                btnParalela.setEnabled(true);
                btnStopC.setVisible(true);
                btnStopC.setEnabled(true);

                if (!puertoAbierto) {
                    try {
                        HiloCliente.levantarPuerto();
                        puertoAbierto = true;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,
                                "Error al abrir puerto. (Registry - Cliente)",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "IP inválida, por favor verifique.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            new HiloCliente().start();
        });

        btnServidor.addActionListener(e -> {
            btnStop.setEnabled(true);
            btnCliente.setEnabled(false);
            serverAddress.setEnabled(false);
            btnServidor.setEnabled(false);

            if (!puertoAbierto) {
                try {
                    HiloServidor.levantarPuerto();
                    puertoAbierto = true;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Error al abrir puerto. (Registry - Servidor)",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            new HiloServidor().start();
        });

        btnParalela.addActionListener(e -> {
        });

        btnStop.addActionListener(e -> new Thread(() -> {
            HiloServidor.detenerRMI();
            btnCliente.setEnabled(true);
            serverAddress.setEnabled(true);
            btnStop.setEnabled(false);
            btnServidor.setEnabled(true);
        }).start());

        btnStopC.addActionListener(e -> new Thread(() -> {
            HiloCliente.detenerRMI();
            serverAddress.setEditable(true);
            btnServidor.setEnabled(true);
            btnStop.setVisible(true);
            btnParalela.setEnabled(false);
            btnStopC.setVisible(false);
        }).start());
    }

    public static void estado(int estado) {
        switch (estado) {
            case 0:
                lblEstado.setText("Desconectado");
                lblEstado.setForeground(Color.RED);
                break;
            case 1:
                lblEstado.setText("Conectado");
                lblEstado.setForeground(Color.GREEN);
                break;
            case 2:
                lblEstado.setText("Conectando...");
                lblEstado.setForeground(Color.ORANGE);
                break;
            case 3:
                lblEstado.setText("Error de conexión");
                lblEstado.setForeground(Color.RED);
                break;
        }
    }

    private boolean validarAddress(String ip) {
        boolean isValid = false;
        for (int i = 0; i < 4; i++) {
            try {
                int num = Integer.parseInt(ip.split("\\.")[i]);
                if (num >= 0 && num <= 255) {
                    isValid = true;
                } else {
                    isValid = false;
                    break;
                }
            } catch (Exception e) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    public static String getServerIp() {
        return serverAddress.getText();
    }
}
