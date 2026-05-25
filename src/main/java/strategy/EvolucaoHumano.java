package strategy;

public class EvolucaoHumano implements EstrategiaEvolucao {
    @Override
    public int calcularNivel(int expAtual) {
        // Os humanos começam no nível 1 e sobem a cada 800 de XP
        return 1 + (expAtual / 800);
    }
}