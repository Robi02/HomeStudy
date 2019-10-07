/**
 * [문제]
 * 예제를 보고 규칙을 유추한 뒤에 별을 찍어 보세요.
 * 
 * [입력]
 * 첫째 줄에 N이 주어진다. N은 항상 3×2k 수이다. (3, 6, 12, 24, 48, ...) (k ≤ 10)
 * 
 * [출력]
 * 첫째 줄부터 N번째 줄까지 별을 출력한다.
 * 
 * [예제 입력]
 * 1) 24
 * 
 * [예제 출력]
 * 1)
 *                        *                        
 *                       * *                       
 *                      *****                      
 *                     *     *                     
 *                    * *   * *                    
 *                   ***** *****                   
 *                  *           *                  
 *                 * *         * *                 
 *                *****       *****                
 *               *     *     *     *               
 *              * *   * *   * *   * *              
 *             ***** ***** ***** *****             
 *            *                       *            
 *           * *                     * *           
 *          *****                   *****          
 *         *     *                 *     *         
 *        * *   * *               * *   * *        
 *       ***** *****             ***** *****       
 *      *           *           *           *      
 *     * *         * *         * *         * *     
 *    *****       *****       *****       *****    
 *   *     *     *     *     *     *     *     *   
 *  * *   * *   * *   * *   * *   * *   * *   * *  
 * ***** ***** ***** ***** ***** ***** ***** *****
 * 
 */

#include <iostream>

void drawTryangleToBuffer(char *pAryBuf2d[], int width, int heigth, int k)
{
    
}

int main(int argc, char **argv)
{
    int k = 0;

    std::cin >> k;

    int height = k;
    int width = ((k * 2) - 1) + 1;
    char cAryBuf2d[height][width];

    // draw tryangle
    drawTryangleToBuffer(&cAryBuf2d[0][0], width, height, k);

    // print asnwer
    for (int y = 0; y < height; ++y)
    {
        std::cout << cAryBuf2d[height] << std::endl;
    }

    return 0;
}
