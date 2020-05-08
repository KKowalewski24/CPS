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
     * DiscreteFourierTransformation - fou_trans, filename to read, filename to save,
     * algorithm type
     * ---
     * algorithm type abbreviation: def, fast_in, fast_rec
     * <p>
     * InverseDiscreteFourierTransformation - inver_fou_trans ilename to read, filename to save,
     * algorithm type
     * ---
     * algorithm type abbreviation: def, fast_in
     * <p>
     * CosineTransformation - cos_trans, filename to read, filename to save, algorithm type
     * algorithm type abbreviation: def, fast_in
     * <p>
     * WalshHadamardTransformation - wals_trans, filename to read, filename to save, algorithm type
     * algorithm type abbreviation: def, fast_in
     * <p>
     * WaveletTransformation - wave_trans, filename to read, filename to save, wavelet type
     * wavelet type abbreviation: DB4, DB6, DB8
     * <p>
     * Comparison - comp, first filename to read, second filename to read
     * <p>
     * Draw charts - draw, filenames to read...
     * <p>
     * Convolution - conv, first filename to read, second filename to read, filename to save
     * <p>
     * Correlation - corr, first filename to read, second filename to read, filename to save
     
'''

JAR_NAME = "cps-0.0.1-jar-with-dependencies.jar"
TXT = "*.txt"
PNG = "*.png"
JAR = "*.jar"
DATA_EXTENSION = "*data"

GENERATE = "generate"
REPRESENT = "represent"
ADD = "add"
SUBTRACT = "sub"
MULTIPLY = "mult"
DIVIDE = "div"
CONVOLUTION = "conv"
CORRELATION = "corr"
SAMPLING = "sampl"
QUANTIZATION = "quant"
EVEN_QUANTIZATION_WITH_TRUNCATION = "qu_trun"
EVEN_QUANTIZATION_WITH_ROUNDING = "qu_roud"
RECONSTRUCTION = "recon"
ZERO_ORDER_EXTRAPOLATION = "zero_order"
FIRST_ORDER_INTERPOLATION = "first_order"
RECONSTRUCTION_BASED_FUNCTION_SINC = "sinc"
DISCRETE_FOURIER_TRANSFORMATION = "fou_trans"
COSINE_TRANSFORMATION = "cos_trans"
WALSH_HADAMARD_TRANSFORMATION = "wals_trans"
WAVELET_TRANSFORMATION = "wave_trans"
BY_DEFINITION = "def"
FAST_TRANSFORMATION_IN_SITU = "fast_in"
FAST_TRANSFORMATION_RECURSIVE = "fast_rec"
DB4 = "DB4"
DB6 = "DB6"
DB8 = "DB8"
COMPARISON = "comp"
DRAW_CHARTS = "draw"

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


def clean_project_directories(remove_jar: bool) -> None:
    script_directory = pathlib.Path(os.getcwd())
    remove_files(glob.glob(TXT))
    remove_files(glob.glob(PNG))
    remove_files(glob.glob(DATA_EXTENSION))
    if remove_jar:
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
def task3_prepare_filtered() -> None:
    run_jar([GENERATE, "sin1.data", SINUSOIDAL_SIGNAL, "0", "1", "2", "0.333333"])
    run_jar([GENERATE, "sin2.data", SINUSOIDAL_SIGNAL, "0", "1", "2", "0.05"])
    run_jar(["add", "sin1.data", "sin2.data", "filtered_continuous.data"])
    run_jar([SAMPLING, "filtered_continuous.data", "filtered.data", "400"])


def task3_filter(filter_type: str, M: str, f_o: str, window: str, experiment_id: str) -> None:
    run_jar([GENERATE, experiment_id + "_filter.data", filter_type, "400", M, f_o, window])
    run_jar([CONVOLUTION, "filtered.data", experiment_id + "_filter.data",
             experiment_id + "_result.data"])
    run_jar([DRAW_CHARTS, experiment_id + "_filter.data"])
    run_jar([DRAW_CHARTS, experiment_id + "_result.data"])

    remove_files([experiment_id + "_filter.data", experiment_id + "_result.data"])


def task3_filter_reconstruction(filter_type: str, M: str, f_o: str,
                                window: str, experiment_id: str) -> None:
    run_jar([GENERATE, experiment_id + "_filter.data", filter_type, "400", M, f_o, window])
    run_jar([CONVOLUTION, "filtered.data", experiment_id + "_filter.data",
             experiment_id + "_result.data"])
    run_jar([RECONSTRUCTION, experiment_id + "_result.data",
             experiment_id + "_result_reconstr.data", "first_order"])
    run_jar([DRAW_CHARTS, experiment_id + "_filter.data"])
    run_jar([DRAW_CHARTS, experiment_id + "_result_reconstr.data"])

    remove_files([experiment_id + "_filter.data", experiment_id + "_result.data",
                  experiment_id + "_result_reconstr.data"])


def task_3() -> None:
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
    task3_filter_reconstruction("high_fil", "51", "190", "win_ham", "3c")
    task3_filter_reconstruction("high_fil", "51", "195", "win_ham", "3d")
    task3_filter_reconstruction("band_fil", "51", "90", "win_ham", "3e")
    task3_filter_reconstruction("band_fil", "51", "80", "win_ham", "3f")


# TASK4 ----------------------------------------------------------------------- #
def task4_generate_signals(s1_filenames: [], s2_filenames: [], s3_filenames: []) -> None:
    sample_rate = "16"
    pi_divided_by_2 = "1.57079"

    s1_sin1 = "s1_sin1.data"
    s1_sin2 = "s1_sin2.data"
    run_jar([GENERATE, s1_sin1, SINUSOIDAL_SIGNAL, pi_divided_by_2, "64", "2", "2"])
    run_jar([GENERATE, s1_sin2, SINUSOIDAL_SIGNAL, pi_divided_by_2, "64", "5", "0.5"])
    run_jar([ADD, s1_sin1, s1_sin2, s1_filenames[0]])
    run_jar([SAMPLING, s1_filenames[0], s1_filenames[1], sample_rate])
    run_jar([DRAW_CHARTS, s1_filenames[1]])

    s2_sin1 = "s2_sin1.data"
    s2_sin2 = "s2_sin2.data"
    s2_sin1_2 = "s2_sin1_2.data"
    s2_sin3 = "s2_sin3.data"
    run_jar([GENERATE, s2_sin1, SINUSOIDAL_SIGNAL, "0", "64", "2", "2"])
    run_jar([GENERATE, s2_sin2, SINUSOIDAL_SIGNAL, "0", "64", "1", "1"])
    run_jar([GENERATE, s2_sin3, SINUSOIDAL_SIGNAL, "0", "64", "5", "0.5"])
    run_jar([ADD, s2_sin1, s2_sin2, s2_sin1_2])
    run_jar([ADD, s2_sin1_2, s2_sin3, s2_filenames[0]])
    run_jar([SAMPLING, s2_filenames[0], s2_filenames[1], sample_rate])
    run_jar([DRAW_CHARTS, s2_filenames[1]])

    s3_sin1 = "s3_sin1.data"
    s3_sin2 = "s3_sin2.data"
    run_jar([GENERATE, s3_sin1, SINUSOIDAL_SIGNAL, "0", "64", "5", "2"])
    run_jar([GENERATE, s3_sin2, SINUSOIDAL_SIGNAL, "0", "64", "1", "0.25"])
    run_jar([ADD, s3_sin1, s3_sin2, s3_filenames[0]])
    run_jar([SAMPLING, s3_filenames[0], s3_filenames[1], sample_rate])
    run_jar([DRAW_CHARTS, s3_filenames[1]])

    pass


def task4_single_transformation(operation_type: str, algorithm_type: str,
                                file_in: str, file_out: str, experiment_id: str) -> None:
    run_jar([operation_type, file_in, experiment_id + file_out, algorithm_type])
    run_jar([DRAW_CHARTS, experiment_id + file_out])
    pass


def task4_series_transformation(filenames: []) -> None:
    task4_single_transformation(DISCRETE_FOURIER_TRANSFORMATION, BY_DEFINITION,
                                filenames[1], filenames[2], "1_")
    task4_single_transformation(DISCRETE_FOURIER_TRANSFORMATION, FAST_TRANSFORMATION_IN_SITU,
                                filenames[1], filenames[2], "2_")
    task4_single_transformation(DISCRETE_FOURIER_TRANSFORMATION, FAST_TRANSFORMATION_RECURSIVE,
                                filenames[1], filenames[2], "3_")
    task4_single_transformation(COSINE_TRANSFORMATION, BY_DEFINITION,
                                filenames[1], filenames[2], "4_")
    task4_single_transformation(COSINE_TRANSFORMATION, FAST_TRANSFORMATION_IN_SITU,
                                filenames[1], filenames[2], "5_")
    task4_single_transformation(WALSH_HADAMARD_TRANSFORMATION, BY_DEFINITION,
                                filenames[1], filenames[2], "6_")
    task4_single_transformation(WALSH_HADAMARD_TRANSFORMATION, FAST_TRANSFORMATION_IN_SITU,
                                filenames[1], filenames[2], "7_")
    task4_single_transformation(WAVELET_TRANSFORMATION, DB4,
                                filenames[1], filenames[2], "8_")
    pass


def task_4() -> None:
    s1_filenames = ["sinus_s1.data", "sinus_sampling_s1.data", "sinus_sampling_trans_s1.data"]
    s2_filenames = ["sinus_s2.data", "sinus_sampling_s2.data", "sinus_sampling_trans_s2.data"]
    s3_filenames = ["sinus_s3.data", "sinus_sampling_s3.data", "sinus_sampling_trans_s3.data"]
    task4_generate_signals(s1_filenames, s2_filenames, s3_filenames)

    task4_series_transformation(s1_filenames)
    task4_series_transformation(s2_filenames)
    task4_series_transformation(s3_filenames)
    pass


# ----------------------------------------------------------------------------- #
def main() -> None:
    if len(sys.argv) == 2 and (sys.argv[1] == "build" or sys.argv[1] == "-b"):
        build_jar()
    elif len(sys.argv) >= 2 and (sys.argv[1] == "clean" or sys.argv[1] == "-c"):
        if len(sys.argv) == 3 and (sys.argv[2] == "jar" or sys.argv[2] == "-j"):
            clean_project_directories(True)
        else:
            clean_project_directories(False)
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
