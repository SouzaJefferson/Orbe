package strategy;

public class EvolucaoElfo implements EstrategiaEvolucao {
    @Override
    public int calcularNivel(int expAtual) {
        // Os elfos começam no nível 1 e sobem a cada 1200 de XP
        return 1 + (expAtual / 500);
    }
}