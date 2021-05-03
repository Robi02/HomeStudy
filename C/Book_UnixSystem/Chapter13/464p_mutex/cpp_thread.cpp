#include <iostream>
#include <pthread.h>
#include <sys/unistd.h>

using namespace std;

class ThreadHandler
{
    public:
    bool make_thread();
    static void *run_thread(void *_arg);

    bool get_isRun() { return isRun; }
    void set_isRun(bool val) { isRun = val; }

    private:
    bool isRun;
    pthread_t subTh_t;
};

bool ThreadHandler::make_thread()
{
    bool rtVal = true;

    if (pthread_create(&subTh_t, NULL, ThreadHandler::run_thread, NULL))
    {
        cout << "스레드 생성 실패." << endl;
    }

    return rtVal;
}

void* ThreadHandler::run_thread(void *_arg)
{
    while (1)
    {
        cout << "스레드 실행 중." << endl;
        sleep(1);
    }

    return NULL;
}

int main()
{
    ThreadHandler *thrHandler = new ThreadHandler();

    if (thrHandler->make_thread())
    {
        thrHandler->set_isRun(true);
    }
    else
    {
        thrHandler->set_isRun(false);
    }

    pthread_exit(0);
}