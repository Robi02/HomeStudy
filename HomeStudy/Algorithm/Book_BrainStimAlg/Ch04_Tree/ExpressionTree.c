#include "ExpressionTree.h"

ETNode* ET_CreateNode(ElementType NewData)
{
    ETNode* NewNode = (ETNode*)malloc(sizeof(ETNode));
    NewNode->Left = NULL;
    NewNode->Right = NULL;
    NewNode->Data = (ElementType)malloc(strlen(NewData));

    return NewNode;
}

void ET_DestroyNode(ETNode* Node)
{
    if (Node != NULL)
    {
        if (Node->Data != NULL)
        {
            memset(Node->Data, 0x00, strlen(Node->Data));
            free (Node->Data);
        }

        Node->Left = NULL;
        Node->Right = NULL;
        Node->Data = NULL;
        free(Node);
    }
}

void ET_DestroyTree(ETNode* Root)
{
    if (Root == NULL) return;

    ET_DestroyTree(Root->Left);
    ET_DestroyTree(Root->Right);
    ET_DestroyTree(Root);
}

void ET_PreorderPrintTree(ETNode* Node)
{
    if (Node == NULL) return;

    fprintf(stdout, " %s", Node->Data);
    ET_PreorderPrintTree(Node->Left);
    ET_PreorderPrintTree(Node->Right);
}

void ET_InorderPrintTree(ETNode* Node)
{
    if (Node == NULL) return;

    fprintf(stdout, "(");
    ET_InorderPrintTree(Node->Left);
    fprintf(stdout, "%s", Node->Data);
    ET_InorderPrintTree(Node->Right);
    fprintf(stdout, ")");
}

void ET_PostorderPrintTree(ETNode* Node)
{
    if (Node == NULL) return;

    ET_PostorderPrintTree(Node->Left);
    ET_PostorderPrintTree(Node->Right);
    fprintf(stdout, " %s", Node->Data);
}

void ET_BuildExpressionTree(char* PostfixExpression, ETNode** Node)
{
    int     len = strlen(PostfixExpression) - 1;
    char    Token = PostfixExpression[len];

    // 이 함수만 다시...

    fprintf(stdout, "%c\n", Token);
    PostfixExpression[len] = '\0'; // ET_CreateNode 함수 내에서 strlen()으로 길이를 구하기 때문에 이 구문 위치 바꾸면 위험하다

    switch (Token)
    {
        case '+': case '-': case '*': case '/':
        {
            (*Node) = ET_CreateNode(&Token);
            ET_BuildExpressionTree(PostfixExpression, &(*Node)->Right);
            ET_BuildExpressionTree(PostfixExpression, &(*Node)->Left);
            break;
        }
        default:
        {
            if (Token >= '0' && Token <= '9')
            {
                char *pBgn = &PostfixExpression[len];
                char *pEnd = pBgn;
                char numBuf[255];

                PostfixExpression[len] = Token;

                fprintf(stdout, "test_postfixexp : %s\n", PostfixExpression);

                while (pBgn != PostfixExpression && *pBgn >= '0' && *pBgn <= '9') --pBgn;

                memset(numBuf, 0x00, sizeof(numBuf));
                memcpy(numBuf, pBgn, (pEnd - pBgn));
                memset(pBgn, 0x00, (pEnd - pBgn));

                fprintf(stdout, "pEnd:0x%p(%c), pBgn:0x%p(%c), len:%ld\n", pEnd, *pEnd, pBgn, *pBgn, pEnd - pBgn);
                fprintf(stdout, "test_postfixexp : %s\n", PostfixExpression); // "71*52-/"
                fprintf(stdout, "test_number : %s\n", numBuf);

                (*Node) = ET_CreateNode(numBuf);
            }
            
            break;
        }
    }
}

double ET_Evaluate(ETNode* Tree)
{
    double Result = 0.0;

    if (Tree == NULL) return 0.0;

    switch (Tree->Data[0])
    {
        case '+': case '-': case '*': case '/':
        {
            char Left  = ET_Evaluate(Tree->Left);
            char Right = ET_Evaluate(Tree->Right);

            if      (Tree->Data[0] == '+') Result = Left + Right;
            else if (Tree->Data[0] == '-') Result = Left - Right;
            else if (Tree->Data[0] == '*') Result = Left * Right;
            else if (Tree->Data[0] == '/') Result = Left / Right;

            break;
        }
        default:
        {
            Result = (double)atof(Tree->Data);
            break;
        }
    }

    return Result;
}

int ET_Test_main()
{
    ETNode* Root = NULL;
    char PostfixExpression[20] = "71*52-/";

    ET_BuildExpressionTree(PostfixExpression, &Root);

    fprintf(stdout, "Preorder...\n");
    ET_PreorderPrintTree(Root);
    fprintf(stdout, "\n\n");

    fprintf(stdout, "Inorder...\n");
    ET_InorderPrintTree(Root);
    fprintf(stdout, "\n\n");

    fprintf(stdout, "Postorder...\n");
    ET_PostorderPrintTree(Root);
    fprintf(stdout, "\n\n");

    fprintf(stdout, "Evaluation Result : %lf\n", ET_Evaluate(Root));

    ET_DestroyTree(Root);

    return 0;
}

int main()
{
    return ET_Test_main();
}