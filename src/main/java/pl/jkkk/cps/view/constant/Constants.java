package pl.jkkk.cps.view.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Constants {

    /*------------------------ FIELDS REGION ------------------------*/
    public static final String PATH_MAIN_PANEL = "/panel/MainPanel.fxml";
    public static final String TITLE_MAIN_PANEL = "Main Panel";

    public static final String PATH_LINE_CHART_PANEL = "/panel/LineChartPanel.fxml";
    public static final String TITLE_LINE_CHART_PANEL = "Line Chart Panel";

    public static final String PATH_BAR_CHART_PANEL = "/panel/BarChartPanel.fxml";
    public static final String TITLE_BAR_CHART_PANEL = "Bar Chart Panel";

    public static final String PATH_CSS_STYLING = "/PanelStyles.css";

    public static final List<String> SIGNAL_TYPE_LIST = Stream.of(
            "szum o rozkładzie jednostajnym",
            "szum gaussowski",
            "sygnał sinusoidalny",
            "sygnał sinusoidalny wyprostowany jednopołówkowo",
            "sygnał sinusoidalny wyprostowany dwupołówkowo",
            "sygnał prostokątny",
            "sygnał prostokątny symetryczny",
            "sygnał trójkątny",
            "skok jednostkowy",
            "impuls jednostkowy",
            "szum impulsowy"
    ).collect(Collectors.toCollection(ArrayList::new));

    public static final List<String> OPERATION_TYPE_LIST = Stream.of(
            "dodawanie",
            "odejmowanie",
            "mnożenie",
            "dzielenie"
    ).collect(Collectors.toCollection(ArrayList::new));

    /*------------------------ METHODS REGION ------------------------*/
    private Constants() {
    }
}
    