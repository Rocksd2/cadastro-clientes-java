package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa uma compra realizada por um cliente.
 */
public class Compra {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final int id;
    private final String descricao;
    private final double valor;
    private final LocalDateTime dataHora;

    public Compra(int id, String descricao, double valor) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.dataHora = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getDataHoraFormatada() {
        return dataHora.format(FORMATTER);
    }

    @Override
    public String toString() {
        return String.format("#%d | %s | R$ %.2f | Data: %s",
                id, descricao, valor, getDataHoraFormatada());
    }
}
