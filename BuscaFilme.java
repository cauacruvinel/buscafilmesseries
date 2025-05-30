package org.example;

import com.google.gson.JsonArray; /*Listas de dados precedentes do JSON
                                    (formato de dados que realiza troca de informações na WEB, *neste código, realiza apenas requisição de informações.).*/

import com.google.gson.JsonObject; //Objetos de dados precedentes do JSON.
import com.google.gson.JsonParser; //Conversão de textos em um JSON manipulável.

import javax.swing.*; //Importação da biblioteca p/ interface gráfica.

import java.io.BufferedReader; //Importa a classe BufferedReader.

import java.io.InputStreamReader;
/*O InputStreamReader é tipo um tradutor que ajuda o Java a entender o que foi digitado no teclado.
E o import java.io.InputStreamReader; é só o jeito de dizer pro Java:
"Ei, eu vou usar esse tradutor aqui, beleza?"*/

import java.net.HttpURLConnection;
import java.net.URL;
//Bibliotecas de importação padrão do HTTP, utilizadas para que o programa acesse os links pela API.

public class BuscaFilme {

    private static final String API_KEY = "f4362c0aad6fa32e548579223c0d7fed"; //Chave API a ser usada no acesso ao site.
    private static final String BASE_URL = "https://api.themoviedb.org/3/search/movie";

    public static String buscarFilme(String nomeFilme) {
        try { // Tratamento de exceção, se a condição não for efetuada, aparecerá outro resultado.
            String urlStr = BASE_URL + "?api_key=" + API_KEY + "&query=" + nomeFilme.replace(" ", "%20") + "&language=pt-BR";
            //Montagem da URL final de busca, a qual ir� ser inserida automaticamente no campo de busca do navegador.

            URL url = new URL(urlStr); //Objeto que possibilitará a conexão.

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection(); //Abertura da conex�o com o navegador.
            conexao.setRequestMethod("GET"); //M�todo que realizará a requisição no site, com um parâmetro GET, por ser busca de dados.

            BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream())); //Preparação da leitura da resposta da API.
                                                                                                        //Armazena os caracteres em buffer (Espaço temporário na memória do computador que pode ser manipulado no Java.)

            StringBuilder resposta = new StringBuilder(); //Objeto que receberá a resposta, pertence a classe StringBuilder, utilizada para ampliar a possibilidade de tratamento de Strings.
            String linha;

            while ((linha = leitor.readLine()) != null) {
                resposta.append(linha);
            }

            leitor.close();

            // Agora tratamos a resposta JSON.
            JsonObject json = JsonParser.parseString(resposta.toString()).getAsJsonObject();
            // Linha que pega a String JSON, e a transforma em um objeto da classe JSONObject.
            JsonArray resultados = json.getAsJsonArray("results");
            // Linha que pega o texto de resultado e o transforma em um Array que contém os filmes encotrados.

            if (resultados.size() > 0) {
                JsonObject primeiroResultado = resultados.get(0).getAsJsonObject();
                String titulo = primeiroResultado.get("title").getAsString();
                String sinopse = primeiroResultado.get("overview").getAsString();
                String dataLancamento = primeiroResultado.get("release_date").getAsString();

                JTextArea Areatexto = new JTextArea(30, 50);
                Areatexto.setText("Título:\n" + titulo +
                        "\n\nData de Lançamento: " + dataLancamento +
                        "\n\nSinopse:\n" + sinopse);
                Areatexto.setWrapStyleWord(true);
                Areatexto.setLineWrap(true);
                Areatexto.setEditable(false);
                Areatexto.setOpaque(false);

                JOptionPane.showMessageDialog(null, Areatexto);

            } else {
                JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado para essa porcaria de filme: " + nomeFilme);
            }

        } catch (Exception e) { //Segundo resultado do tratamento de exceção.
            JOptionPane.showMessageDialog(null,"Erro na busca irmão: " + e.getMessage());
        }
        return nomeFilme;
    }
}
