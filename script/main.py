import glob
import os
import pathlib
import platform
import subprocess
import sys

'''

How to use
To build run `python main.py build` or `python main.py -b` in order to build jar file
To run program python main.py run $NUMBER$` or `python main.py -r $NUMBER$` 
where $NUMBER$ is task number - 2,3,4

 
if empty array is passed jar is being called with 0 args
if you want to pass args just place them in array as a string

     * Params for cmd mode
     * <p>
     * Generate - generate, filename to save, signal type, params for signal
     * Signal type abbreviation:
     * uni_noise - rangeStart, rangeLength, amplitude
     * gauss_noise -rangeStart, rangeLength, amplitude
     * sin - rangeStart, rangeLength, amplitude, term
     * sin_one_half - rangeStart, rangeLength, amplitude, term
     * sin_two_half - rangeStart, rangeLength, amplitude, term
     * rect - rangeStart, rangeLength, amplitude, term, fulfillment
     * rect_symm - rangeStart, rangeLength, amplitude, term, fulfillment
     * triang - rangeStart, rangeLength, amplitude, term, fulfillment
     * unit_jump - rangeStart, rangeLength, amplitude, jumpMoment
     * unit_impulse - rangeStart, rangeLength, sampleRate, amplitude, jumpMoment.intValue()
     * impulse_noise - rangeStart, rangeLength, sampleRate, amplitude,probability
     * --
     * low_fil - sampleRate, filterRow, cuttingFrequency,
     * windowType: win_rect || win_ham || win_han || win_bla
     * --
     * band_fil - sampleRate, filterRow, cuttingFrequency,
     * windowType: win_rect || win_ham || win_han || win_bla
     * --
     * high_fil - sampleRate, filterRow, cuttingFrequency,
     * windowType: win_rect || win_ham || win_han || win_bla
     * <p>
     * Sampling - sampl, filename to read, filename to save, sampleRate
     * <p>
     * Quantization - quant, filename to read, filename to save, type, quantization level
     * Type abbreviation :
     * qu_trun, qu_roud
     * <p>
     * Reconstruction - recon, filename to read, filename to save, type, sinc param(only for sinc)
     * Type abbreviation:
     * zero_order, first_order, sinc
     * <p>
     * Comparison - comp, first filename to read, second filename to read
     * <p>
     * Draw charts - draw, filenames to read...
     * <p>
     * Convolution - conv, first filename to read, second filename to read, filename to save
     * 
     * Correlation - corr, first filename to read, second filename to read, filename to save

'''

JAR_NAME = "cps-0.0.1-jar-with-dependencies.jar"
TXT = "*.txt"
PNG = "*.png"
JAR = "*.jar"
DATA_EXTENSION = "*data"

GENERATE = "generate"
SAMPLING = "sampl"
QUANTIZATION = "quant"
EVEN_QUANTIZATION_WITH_TRUNCATION = "qu_trun"
EVEN_QUANTIZATION_WITH_ROUNDING = "qu_roud"
RECONSTRUCTION = "recon"
ZERO_ORDER_EXTRAPOLATION = "zero_order"
FIRST_ORDER_INTERPOLATION = "first_order"
RECONSTRUCTION_BASED_FUNCTION_SINC = "sinc"
COMPARISON = "comp"
DRAW_CHARTS = "draw"
CONVOLUTION = "conv"

UNIFORM_NOISE = "uni_noise"
GAUSSIAN_NOISE = "gauss_noise"
SINUSOIDAL_SIGNAL = "sin"
SINUSOIDAL_RECTIFIED_ONE_HALF_SIGNAL = "sin_one_half"
SINUSOIDAL_RECTIFIED_IN_TWO_HALVES = "sin_two_half"
RECTANGULAR_SIGNAL = "rect"
SYMMETRICAL_RECTANGULAR_SIGNAL = "rect_symm"
TRIANGULAR_SIGNAL = "triang"
UNIT_JUMP = "unit_jump"
UNIT_IMPULSE = "unit_impulse"
IMPULSE_NOISE = "impulse_noise"
LOW_PASS_FILTER = "low_fil"
BAND_PASS_FILTER = "band_fil"
HIGH_PASS_FILTER = "high_fil"

RECTANGULAR_WINDOW = "win_rect"
HAMMING_WINDOW = "win_ham"
HANNING_WINDOW = "win_han"
BLACKMAN_WINDOW = "win_bla"


# UTIL ------------------------------------------------------------------------ #
def build_jar() -> None:
    script_directory = pathlib.Path(os.getcwd())
    os.chdir(script_directory.parent)
    if platform.system().lower() == "windows":
        subprocess.call("mvnw.cmd clean package", shell=True)
        subprocess.call("copy target\\" + JAR_NAME + " " + str(script_directory), shell=True)
    elif platform.system().lower() == "linux":
        subprocess.call("./mvnw clean package", shell=True)
        subprocess.call("copy target/" + JAR_NAME + " " + str(script_directory), shell=True)


def remove_files(filenames: []) -> None:
    for it in filenames:
        os.remove(it)


def clean_project_directories():
    script_directory = pathlib.Path(os.getcwd())
    remove_files(glob.glob(TXT))
    remove_files(glob.glob(PNG))
    remove_files(glob.glob(DATA_EXTENSION))
    remove_files(glob.glob(JAR))

    os.chdir(script_directory.parent)
    remove_files(glob.glob(TXT))
    remove_files(glob.glob(PNG))
    pass


def append_array(main_array: [], child_array: []) -> []:
    for it in child_array:
        main_array.append(it)

    return main_array


def run_jar(args: []) -> None:
    subprocess.call(append_array(["java", "-jar", JAR_NAME], args))


# TASK2 ----------------------------------------------------------------------- #
def task2_reconstruction_single_experiment(signal_type: str, signal_args: [],
                                           sample_rate: str, reconstruction_type: str,
                                           sinc_param: str = "-15") -> None:
    filename_orig_chart = "original_chart"
    filename_samp_output = "sampling_output"
    filename_recon_output = "recon_output"

    run_jar(append_array([GENERATE, filename_orig_chart, signal_type], signal_args))
    run_jar([SAMPLING, filename_orig_chart, filename_samp_output, sample_rate])
    if sinc_param == "-15":
        run_jar([RECONSTRUCTION, filename_samp_output, filename_recon_output, reconstruction_type])
    else:
        run_jar([RECONSTRUCTION, filename_samp_output, filename_recon_output,
                 reconstruction_type, sinc_param])
    run_jar([COMPARISON, filename_orig_chart, filename_recon_output])
    run_jar([DRAW_CHARTS, filename_orig_chart, filename_recon_output])

    remove_files([filename_orig_chart, filename_samp_output, filename_recon_output])
    pass


def task2_quantization_single_experiment(signal_type: str, signal_args: [],
                                         sample_rate: str, quantization_type: str,
                                         quantization_level: str) -> None:
    filename_orig_chart = "original_chart"
    filename_samp_output = "sampling_output"
    filename_quant_output = "quant_output"

    run_jar(append_array([GENERATE, filename_orig_chart, signal_type], signal_args))
    run_jar([SAMPLING, filename_orig_chart, filename_samp_output, sample_rate])
    run_jar([QUANTIZATION, filename_samp_output,
             filename_quant_output, quantization_type, quantization_level])
    run_jar([COMPARISON, filename_samp_output, filename_quant_output])
    run_jar([DRAW_CHARTS, filename_samp_output, filename_quant_output])

    remove_files([filename_orig_chart, filename_samp_output, filename_quant_output])
    pass


def task2_series1_A() -> None:
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "21",
                                           RECONSTRUCTION_BASED_FUNCTION_SINC, "100")
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "30",
                                           RECONSTRUCTION_BASED_FUNCTION_SINC, "100")
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "50",
                                           RECONSTRUCTION_BASED_FUNCTION_SINC, "100")
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "100",
                                           RECONSTRUCTION_BASED_FUNCTION_SINC, "100")

    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "30",
                                           ZERO_ORDER_EXTRAPOLATION)
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "50",
                                           ZERO_ORDER_EXTRAPOLATION)
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "100",
                                           ZERO_ORDER_EXTRAPOLATION)

    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "30",
                                           FIRST_ORDER_INTERPOLATION)
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "50",
                                           FIRST_ORDER_INTERPOLATION)
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "100",
                                           FIRST_ORDER_INTERPOLATION)
    pass


def task2_series1_B() -> None:
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "50",
                                           RECONSTRUCTION_BASED_FUNCTION_SINC, "100")
    task2_reconstruction_single_experiment(RECTANGULAR_SIGNAL,
                                           ["0", "0.5", "1", "0.1", "0.5"], "50",
                                           RECONSTRUCTION_BASED_FUNCTION_SINC, "100")
    task2_reconstruction_single_experiment(TRIANGULAR_SIGNAL,
                                           ["0", "0.5", "1", "0.1", "0.5"], "50",
                                           RECONSTRUCTION_BASED_FUNCTION_SINC, "100")

    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "50",
                                           ZERO_ORDER_EXTRAPOLATION)
    task2_reconstruction_single_experiment(RECTANGULAR_SIGNAL,
                                           ["0", "0.5", "1", "0.1", "0.5"], "50",
                                           ZERO_ORDER_EXTRAPOLATION)
    task2_reconstruction_single_experiment(TRIANGULAR_SIGNAL,
                                           ["0", "0.5", "1", "0.1", "0.5"], "50",
                                           ZERO_ORDER_EXTRAPOLATION)

    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "50",
                                           FIRST_ORDER_INTERPOLATION)
    task2_reconstruction_single_experiment(RECTANGULAR_SIGNAL,
                                           ["0", "0.5", "1", "0.1", "0.5"], "50",
                                           FIRST_ORDER_INTERPOLATION)
    task2_reconstruction_single_experiment(TRIANGULAR_SIGNAL,
                                           ["0", "0.5", "1", "0.1", "0.5"], "50",
                                           FIRST_ORDER_INTERPOLATION)
    pass


def task2_series1_C() -> None:
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "1", "1", "0.143"], "5",
                                           RECONSTRUCTION_BASED_FUNCTION_SINC, "100")
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "1", "1", "0.083"], "5",
                                           RECONSTRUCTION_BASED_FUNCTION_SINC, "100")
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "1", "1", "0.09"], "5",
                                           RECONSTRUCTION_BASED_FUNCTION_SINC, "100")
    pass


def task2_series1_D() -> None:
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "50",
                                           RECONSTRUCTION_BASED_FUNCTION_SINC, "1")
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "50",
                                           RECONSTRUCTION_BASED_FUNCTION_SINC, "2")
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "50",
                                           RECONSTRUCTION_BASED_FUNCTION_SINC, "3")
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "50",
                                           RECONSTRUCTION_BASED_FUNCTION_SINC, "4")
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "50",
                                           RECONSTRUCTION_BASED_FUNCTION_SINC, "5")
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "50",
                                           RECONSTRUCTION_BASED_FUNCTION_SINC, "10")
    task2_reconstruction_single_experiment(SINUSOIDAL_SIGNAL,
                                           ["0", "0.5", "1", "0.1"], "50",
                                           RECONSTRUCTION_BASED_FUNCTION_SINC, "25")
    pass


def task2_series2_A() -> None:
    task2_quantization_single_experiment(SINUSOIDAL_SIGNAL,
                                         ["0", "1", "1", "0.25"], "100",
                                         EVEN_QUANTIZATION_WITH_ROUNDING, "2")
    task2_quantization_single_experiment(SINUSOIDAL_SIGNAL,
                                         ["0", "1", "1", "0.25"], "100",
                                         EVEN_QUANTIZATION_WITH_ROUNDING, "5")
    task2_quantization_single_experiment(SINUSOIDAL_SIGNAL,
                                         ["0", "1", "1", "0.25"], "100",
                                         EVEN_QUANTIZATION_WITH_ROUNDING, "10")

    task2_quantization_single_experiment(RECTANGULAR_SIGNAL,
                                         ["0", "1", "1", "0.25", "0.5"], "100",
                                         EVEN_QUANTIZATION_WITH_ROUNDING, "2")
    task2_quantization_single_experiment(RECTANGULAR_SIGNAL,
                                         ["0", "1", "1", "0.25", "0.5"], "100",
                                         EVEN_QUANTIZATION_WITH_ROUNDING, "5")

    task2_quantization_single_experiment(TRIANGULAR_SIGNAL,
                                         ["0", "1", "1", "0.25", "0.5"], "100",
                                         EVEN_QUANTIZATION_WITH_ROUNDING, "2")
    task2_quantization_single_experiment(TRIANGULAR_SIGNAL,
                                         ["0", "1", "1", "0.25", "0.5"], "100",
                                         EVEN_QUANTIZATION_WITH_ROUNDING, "3")
    task2_quantization_single_experiment(TRIANGULAR_SIGNAL,
                                         ["0", "1", "1", "0.25", "0.5"], "100",
                                         EVEN_QUANTIZATION_WITH_ROUNDING, "5")
    pass


def task_2() -> None:
    task2_series1_A()
    task2_series1_B()
    task2_series1_C()
    task2_series1_D()
    task2_series2_A()
    pass


# TASK3 ----------------------------------------------------------------------- #
def task3_prepare_filtered():
    run_jar([GENERATE, "sin1.data", "sin", "0", "1", "2", "0.333333"])
    run_jar([GENERATE, "sin2.data", "sin", "0", "1", "2", "0.05"])
    run_jar(["add", "sin1.data", "sin2.data", "filtered_continuous.data"])
    run_jar([SAMPLING, "filtered_continuous.data", "filtered.data", "400"])


def task3_filter(filter_type, M, f_o, window, experiment_id):
    run_jar([GENERATE, experiment_id + "_filter.data", filter_type, "400", M, f_o, window])
    run_jar([CONVOLUTION, "filtered.data", experiment_id + "_filter.data",
             experiment_id + "_result.data"])
    run_jar([DRAW_CHARTS, experiment_id + "_filter.data"])
    run_jar([DRAW_CHARTS, experiment_id + "_result.data"])

    remove_files([experiment_id + "_filter.data", experiment_id + "_result.data"])


def task3_filter_reconstr(filter_type, M, f_o, window, experiment_id):
    run_jar([GENERATE, experiment_id + "_filter.data", filter_type, "400", M, f_o, window])
    run_jar([CONVOLUTION, "filtered.data", experiment_id + "_filter.data",
             experiment_id + "_result.data"])
    run_jar([RECONSTRUCTION, experiment_id + "_result.data",
             experiment_id + "_result_reconstr.data", "first_order"])
    run_jar([DRAW_CHARTS, experiment_id + "_filter.data"])
    run_jar([DRAW_CHARTS, experiment_id + "_result_reconstr.data"])

    remove_files([experiment_id + "_filter.data", experiment_id + "_result.data",
                  experiment_id + "_result_reconstr.data"])


def task_3():
    task3_prepare_filtered()

    # windows
    task3_filter("low_fil", "51", "5", "win_rect", "1a")
    task3_filter("low_fil", "51", "5", "win_ham", "1b")
    task3_filter("low_fil", "51", "5", "win_han", "1c")
    task3_filter("low_fil", "51", "5", "win_bla", "1d")

    # rows
    task3_filter("low_fil", "41", "5", "win_ham", "2a")
    task3_filter("low_fil", "31", "5", "win_ham", "2b")
    task3_filter("low_fil", "21", "5", "win_ham", "2c")

    # frequencies and filters
    task3_filter("low_fil", "51", "3", "win_ham", "3a")
    task3_filter("low_fil", "51", "2", "win_ham", "3b")
    task3_filter_reconstr("high_fil", "51", "190", "win_ham", "3c")
    task3_filter_reconstr("high_fil", "51", "195", "win_ham", "3d")
    task3_filter_reconstr("band_fil", "51", "90", "win_ham", "3e")
    task3_filter_reconstr("band_fil", "51", "80", "win_ham", "3f")


# TASK4 ----------------------------------------------------------------------- #
def task_4() -> None:
    pass


# ----------------------------------------------------------------------------- #
def main() -> None:
    if len(sys.argv) == 2 and (sys.argv[1] == "build" or sys.argv[1] == "-b"):
        build_jar()
    elif len(sys.argv) == 2 and (sys.argv[1] == "clean" or sys.argv[1] == "-c"):
        clean_project_directories()
    elif len(sys.argv) == 3 and (sys.argv[1] == "run" or sys.argv[1] == "-r"):
        if sys.argv[2] == "2":
            task_2()
        elif sys.argv[2] == "3":
            task_3()
        elif sys.argv[2] == "4":
            task_4()

    print("------------------------------------------------------------------------")
    print("FINISHED")
    print("------------------------------------------------------------------------")


if __name__ == "__main__":
    main()
