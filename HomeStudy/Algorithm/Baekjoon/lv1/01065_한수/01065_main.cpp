/**
 * [문제]
 * 어떤 양의 정수 X의 자리수가 등차수열을 이룬다면, 그 수를 한수라고 한다.
 * 등차수열은 연속된 두 개의 수의 차이가 일정한 수열을 말한다.
 * N이 주어졌을 때, 1보다 크거나 같고, N보다 작거나 같은 한수의 개수를 출력하는 프로그램을 작성하시오. 
 * 
 * [입력]
 * 첫째 줄에 1,000보다 작거나 같은 자연수 N이 주어진다.
 * 
 * [출력]
 * 첫째 줄에 1보다 크거나 같고, N보다 작거나 같은 한수의 개수를 출력한다.
 * 
 **/

#include <iostream>
#include <string>

bool isSeq(int n)
{
    if (n < 100)
    {
        return true;
    }

    std::string strNum = std::to_string(n);
    int strNumLen = strNum.length() - 1;
    int dist = (strNum[0] - '0') - (strNum[1] - '0');

    for (int i = 0; i < strNumLen; ++i)
    {
        if (strNum[i] - strNum[i + 1] != dist)
        {
            return false;
        }
    }

    return true;
}

int main(int argc, char **argv)
{
    int max = 0;

    std::cin >> max;
    ++max;

    int seqCnt = 0;

    for (int i = 1; i < max; ++i)
    {
        if (isSeq(i)) ++seqCnt;
    }

    std::cout << seqCnt << std::endl;
    
    return 0;
}