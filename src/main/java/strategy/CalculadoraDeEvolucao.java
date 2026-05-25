package strategy;

public class CalculadoraDeEvolucao {

    public int subirDeNivel(String raca, int expAtual) {
        EstrategiaEvolucao estrategia;

        // O sistema "pluga" a regra correta dependendo da String
        if (raca != null && raca.equalsIgnoreCase("Elfo")) {
            estrategia = new EvolucaoElfo();
        } else {
            // Se for Humano ou qualquer outra coisa que ainda não criámos, usa a base de Humano
            estrategia = new EvolucaoHumano();
        }

        // Executa a matemática isolada e devolve o nível novo
        return estrategia.calcularNivel(expAtual);
    }
}