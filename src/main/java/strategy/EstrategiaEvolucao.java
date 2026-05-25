package strategy;

public interface EstrategiaEvolucao {
    // Qualquer classe que assinar este contrato terá de criar a matemática para este método
    int calcularNivel(int expAtual);
}