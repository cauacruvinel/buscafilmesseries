package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BuscaSerie {
    private static final String API_KEY = "f4362c0aad6fa32e548579223c0d7fed"; //Chave API a ser usada no acesso ao site.
    private static final String BASE_URL = "https://api.themoviedb.org/3/search/tv";

    public static String buscarSerie(String nomeSerie) {
        try { // Tratamento de exceção, se a condição não for efetuada, aparecerá outro resultado.
            String urlStr = BASE_URL + "?api_key=" + API_KEY + "&query=" + nomeSerie.replace(" ", "%20") + "&language=pt-BR";
            //Montagem da URL final de busca, a qual irá ser inserida automaticamente no campo de busca do navegador.

            URL url = new URL(urlStr); //Objeto que possibilitará a conexão.

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection(); //Abertura da conex�o com o navegador.
            conexao.setRequestMethod("GET"); //M�todo que realizar� a requisi��o no site, com um par�metro GET, por ser busca de dados.

            BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream())); //Prepara��o da leitura da resposta da API.

            StringBuilder resposta = new StringBuilder(); //Objeto que receber� a resposta.
            String linha;

            while ((linha = leitor.readLine()) != null) {
                resposta.append(linha); //Vai escanear toda a resposta obtida da API.
            }

            leitor.close();

            // Agora tratamos a resposta JSON.
            JsonObject json = JsonParser.parseString(resposta.toString()).getAsJsonObject();
            // Linha que pega a String JSON, e a transforma em um objeto da classe JSONObject.
            JsonArray resultados = json.getAsJsonArray("results");
            // Linha que pega o texto de resultado e o transforma em um Array que cont�m as s�ries encontradas.

            if (resultados.size() > 0) {
                JsonObject primeiroResultado = resultados.get(0).getAsJsonObject();
                String titulo = primeiroResultado.get("name").getAsString();
                String sinopse = primeiroResultado.get("overview").getAsString();
                String dataLancamento = primeiroResultado.get("first_air_date").getAsString();

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
                JOptionPane.showMessageDialog(null,"Nenhum resultado encontrado para essa porcaria de série: " + nomeSerie);
            }

        } catch (Exception e) { //Segundo resultado do tratamento de exce��o.
            JOptionPane.showMessageDialog(null, "Erro na busca irmão: " + e.getMessage());
        }
        return nomeSerie;
    }
}