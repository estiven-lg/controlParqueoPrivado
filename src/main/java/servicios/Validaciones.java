package servicios;

public class Validaciones {

    public static boolean validarCUI(long cuiInt) {

        String cui = Long.toString(cuiInt);
        // 2990 16536 02 08
        if (cui.length() != 13) {
            return false;
        }

        int depto = Integer.parseInt(cui.substring(9, 11), 10);
        int muni = Integer.parseInt(cui.substring(11, 13));
        String numero[] = cui.substring(0, 8).split("");
        int verificador = Integer.parseInt(cui.substring(8, 9));

        int[] munisPorDepto = new int[]{
            /* 01 - Guatemala tiene: */17 /* municipios. */,
            /* 02 - El Progreso tiene: */ 8 /* municipios. */,
            /* 03 - Sacatepéquez tiene: */ 16 /* municipios. */,
            /* 04 - Chimaltenango tiene: */ 16 /* municipios. */,
            /* 05 - Escuintla tiene: */ 13 /* municipios. */,
            /* 06 - Santa Rosa tiene: */ 14 /* municipios. */,
            /* 07 - Sololá tiene: */ 19 /* municipios. */,
            /* 08 - Totonicapán tiene: */ 8 /* municipios. */,
            /* 09 - Quetzaltenango tiene: */ 24 /* municipios. */,
            /* 10 - Suchitepéquez tiene: */ 21 /* municipios. */,
            /* 11 - Retalhuleu tiene: */ 9 /* municipios. */,
            /* 12 - San Marcos tiene: */ 30 /* municipios. */,
            /* 13 - Huehuetenango tiene: */ 32 /* municipios. */,
            /* 14 - Quiché tiene: */ 21 /* municipios. */,
            /* 15 - Baja Verapaz tiene: */ 8 /* municipios. */,
            /* 16 - Alta Verapaz tiene: */ 17 /* municipios. */,
            /* 17 - Petén tiene: */ 14 /* municipios. */,
            /* 18 - Izabal tiene: */ 5 /* municipios. */,
            /* 19 - Zacapa tiene: */ 11 /* municipios. */,
            /* 20 - Chiquimula tiene: */ 11 /* municipios. */,
            /* 21 - Jalapa tiene: */ 7 /* municipios. */,
            /* 22 - Jutiapa tiene: */ 17 /* municipios. */};

        if (depto == 0 || muni == 0) {
            System.out.println("CUI con código de municipio o departamento inválido.");
            return false;
        }

        if (depto > munisPorDepto.length) {
            System.out.println("CUI con código de departamento inválido.");
            return false;
        }

        if (muni > munisPorDepto[depto - 1]) {
            System.out.println("CUI con código de municipio inválido.");
            return false;
        }

        // Se verifica el correlativo con base
        // en el algoritmo del complemento 11.
        int total = 0;

        for (var i = 0; i < numero.length; i++) {
            total += Integer.parseInt(numero[i]) * (i + 2);
        }

        var modulo = (total % 11);

        return modulo == verificador;

    }

    public static boolean validarNIT(String nit) {
        try {

            if (nit.length() != 9) {
                return false;
            }
            int posicionGuion = nit.indexOf("-");

            String[] correlativo = nit.substring(0, 7).split("");
            String digitoVerificador = nit.substring(8, 9);
            int factor = correlativo.length + 1;
            int valor = 0;
            for (int i = 0; i < 7; i++) {
                valor += Integer.parseInt(correlativo[i]) * factor;
                factor -= 1;
            }
            int residuo = valor % 11;
            String resultado = Integer.toString(11 - residuo);

            return digitoVerificador.equals(resultado);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static boolean validarFecha(int dia, int mes, int anio) {
 
        switch (mes) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if (!(dia > 0 && dia <= 31)) {
                    return false;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                if (!(dia > 0 && dia <= 30)) {
                    return false;
                }
                break;
            case 2:
                if (dia > 0 && dia <= 29) {
                    if (dia == 29 && (anio % 4) != 0) {
                        System.out.println("1");
                        return false;
                    }

                    if (!(dia > 0 && dia <= 28) && (anio % 4) != 0) {
                        System.out.println("2");
                        return false;
                    }
                }
                break;
            default:
                return false;

        }
        return true;
    }

    public static boolean validarEnteros(String enteros) throws Exception {
        String[] enterosArray = enteros.split("");
        for (int i = 0; i < enterosArray.length; i++) {
            if (!"0123456789".contains(enterosArray[i])) {
                return false;
            }
        }

        return true;
    }

    public static boolean validarPlacas(String placa)  {
        String[] placaArray = placa.split("");
        if (placa.length() != 7) {
            return false;
        }

        if (placa.toLowerCase().charAt(0) != 'm' && placa.toLowerCase().charAt(0) != 'c' && placa.toLowerCase().charAt(0) != 'p') {
            return false;
        }
        for (int i = 1; i != 4; i++) {
            if (!"0123456789".contains(placaArray[i])) {
                return false;
            }
        }
        for (int i = 4; i != 6; i++) {
            if (!"qwrtypsdfghjklzxcvbnm".contains(placaArray[i].toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}
