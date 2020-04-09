import subprocess

'''

How to use
Copy generated jar to directory where this python script is located, 
the call run_jar() function, 
if empty array is passed jar is being called with 0 args
if you want to pass args just place them in array as a string

'''

JAR_NAME = "cps-0.0.1-jar-with-dependencies.jar"


# ----------------------------------------------------------------------------- #
def run_jar(args):
    command = ["java", "-jar", JAR_NAME]
    for it in args:
        command.append(it)

    subprocess.call(command)


def series_1():
    run_jar(["generate", "uni_noise", "1", "5", "1"])


def series_2():
    run_jar([])


# ----------------------------------------------------------------------------- #
def main():
    series_1()
    # series_2()

    print("------------------------------------------------------------------------")
    print("FINISHED SUCCESSFULLY")
    print("------------------------------------------------------------------------")


if __name__ == "__main__":
    main()
