package com.company;

import java.io.*;

import com.google.gson.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {


        int selection = menu();

        switch (selection) {
            case 1:
                //Nivel 1
                System.out.println("Ingrese el archivo y el archivo de salida separados por un espacio. <entrada.json> <salida.json>");
                String path;
                Scanner input = new Scanner(System.in);
                path = input.nextLine();

                String[] split = path.split("\\s");
                JsonParser parser = new JsonParser();
                FileReader fr = new FileReader(split[0]);
                JsonElement datos = parser.parse(fr);
                System.out.println("Generando archivo.");
                dumpJSONElement(datos, selection, split[1]);

                break;
            case 2:
                //Nivel 2
                System.out.println("Realizando Consulta.");
                JsonParser parserApiCall = new JsonParser();
                try {
                    URL urlForGetRequest = new URL("http://127.0.0.1:8080/periodos/api");
                    String readLine = null;
                    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
                    conection.setRequestMethod("GET");
                    conection.setRequestProperty("Accept","application/json");
                    int responseCode = conection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(conection.getInputStream()));
                        StringBuffer response = new StringBuffer();
                        while ((readLine = in .readLine()) != null) {
                            response.append(readLine);
                        } in .close();
                        // print result
                        JsonElement datosApiCall = parserApiCall.parse(response.toString());
                        System.out.println("Generando archivo.");
                        dumpJSONElement(datosApiCall, selection, "./out.json");
                    } else {
                        System.out.println("GET NOT WORKED");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                break;
            case 4:
                System.exit(1);
                break;
            default:
        }
    }

    private static void dumpJSONElement(JsonElement elemento, int selection, String path) throws FileNotFoundException {
        if (elemento.isJsonObject()) {
            JsonObject obj = elemento.getAsJsonObject();
            JsonArray fechas = (JsonArray) obj.get("fechas");
            String s = obj.get("fechaCreacion").getAsString();
            String e = obj.get("fechaFin").getAsString();
            LocalDate start = LocalDate.parse(s);
            LocalDate end = LocalDate.parse(e);
            List<String> totalDates = new ArrayList<>();
            List<String> totalDatesGive = new ArrayList<>();
            while (!start.isAfter(end)) {
                if(!isInArray(start.toString(), fechas)) {
                    totalDates.add(start.toString());
                }else{
                    totalDatesGive.add(start.toString());
                }
                start = start.plusMonths(1);
            }

            Gson gson = new Gson();
            MiObjeto objOutput = new MiObjeto(obj.get("id").getAsString(), start.toString(), end.toString(), totalDates, totalDatesGive, selection);
            String jsonString = gson.toJson(objOutput);
            PrintStream fileOut = new PrintStream(path);
            System.setOut(fileOut);
            System.out.println(jsonString);

        } else {
            System.out.println("Es otra cosa");
        }
    }

    public static int menu() {

        int selection;
        Scanner input = new Scanner(System.in);

        /***************************************************/

        System.out.println("Escojer un nivel");
        System.out.println("-------------------------\n");
        System.out.println("1 - Desafio Nivel 1");
        System.out.println("2 - Desafio Nivel 2");
        //System.out.println("3 -Desafio Nivel 3");
        System.out.println("4 - Quit");

        selection = input.nextInt();
        return selection;
    }

    public static boolean isInArray(String date, JsonArray arrayFechas)
    {
        boolean isIn= false;
        for (int i = 0; i <arrayFechas.size() ; i++) {
            if(arrayFechas.get(i).getAsString().equals(date))
                isIn = true;
        }
        return isIn;
    }

    static class MiObjeto {
        private String id;
        private String fechaCreacion;
        private String fechaFin;
        private Collection fechasFaltantes = new ArrayList();
        private Collection fechasRecibidas = new ArrayList();
        private MiObjeto(String id, String fecalCreation, String fechaFin, Collection fechas, Collection fechasDadas, int selection) {
            this.id = id;
            this.fechaCreacion = fecalCreation;
            this.fechaFin = fechaFin;
            this.fechasFaltantes = fechas;
            if(selection == 2 ){
                this.fechasRecibidas = fechasDadas;
            }

        }
    }
}
