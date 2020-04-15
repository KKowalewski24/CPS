import os
import pathlib
import platform
import subprocess
import sys

'''

How to use
To build run `python main.py build` or `python main.py -b` in order to build jar file, 
then run program without args to run experiments

 
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

'''

JAR_NAME = "cps-0.0.1-jar-with-dependencies.jar"

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


def run_jar(args: []) -> None:
    command = ["java", "-jar", JAR_NAME]
    for it in args:
        command.append(it)

    subprocess.call(command)


def append_array(main_array: [], child_array: []) -> []:
    for it in child_array:
        main_array.append(it)

    return main_array


# TASK2 ----------------------------------------------------------------------- #
def single_experiment(signal_type: str, signal_args: [],
                      sample_rate: str, reconstruction_type: str,
                      sinc_param: str = "-15") -> None:
    run_jar(append_array([GENERATE, "original_chart", signal_type], signal_args))
    run_jar([SAMPLING, "original_chart", "sampling_output", sample_rate])
    if sinc_param == -15:
        run_jar([RECONSTRUCTION, "sampling_output", "recon_output", reconstruction_type])
    else:
        run_jar([RECONSTRUCTION, "sampling_output", "recon_output",
                 reconstruction_type, sinc_param])
    run_jar([COMPARISON, "original_chart", "recon_output"])
    run_jar([DRAW_CHARTS, "original_chart", "recon_output"])

    pass


def series_A() -> None:
    single_experiment(SINUSOIDAL_SIGNAL, ["0", "0.5", "1", "0.1"], "21",
                      RECONSTRUCTION_BASED_FUNCTION_SINC, "100")

    pass


def series_B() -> None:
    # sin - rangeStart=0, rangeLength, amplitude=1, term
    # wspolczynnik wypelnianienia na 0.5
    # run_jar([])
    pass


def series_C() -> None:
    # run_jar([])
    pass


def task_2() -> None:
    series_A()
    series_B()
    series_C()
    pass


# TASK3 ----------------------------------------------------------------------- #
def task_3() -> None:
    pass


# TASK4 ----------------------------------------------------------------------- #
def task_4() -> None:
    pass


# ----------------------------------------------------------------------------- #
def main() -> None:
    if len(sys.argv) > 1 and (sys.argv[1] == "build" or sys.argv[1] == "-b"):
        build_jar()
    else:
        task_2()
        # task_3()
        # task_4()
        pass

    print("------------------------------------------------------------------------")
    print("FINISHED SUCCESSFULLY")
    print("------------------------------------------------------------------------")


if __name__ == "__main__":
    main()
