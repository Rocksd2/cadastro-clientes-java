package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa um cliente no sistema.
 */
public class Cliente {
    private final int id;
    private String nome;
    private String email;
    private String telefone;
    private final List<Compra> historicoCompras;

    public Cliente(int id, String nome, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.historicoCompras = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Compra> getHistoricoCompras() {
        return Collections.unmodifiableList(historicoCompras);
    }

    public void adicionarCompra(Compra compra) {
        this.historicoCompras.add(compra);
    }

    public double getTotalGasto() {
        return historicoCompras.stream()
                .mapToDouble(Compra::getValor)
                .sum();
    }

    public int getQuantidadeCompras() {
        return historicoCompras.size();
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %-3d | Nome: %-22s | E-mail: %-25s | Tel: %-15s | Compras: %d (Total: R$ %.2f)",
            id, nome, email, telefone, getQuantidadeCompras(), getTotalGasto()
        );
    }
}
