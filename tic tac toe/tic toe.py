# Tic-Tac-Toe
board = [' ' for _ in range(9)]


def display_board():
    print('-------------')
    print('|', board[0], '|', board[1], '|', board[2], '|')
    print('-------------')
    print('|', board[3], '|', board[4], '|', board[5], '|')
    print('-------------')
    print('|', board[6], '|', board[7], '|', board[8], '|')
    print('-------------')

def check_win(mark):
   
    for i in range(0, 9, 3):
        if board[i] == board[i+1] == board[i+2] == mark:
            return True

 
    for i in range(3):
        if board[i] == board[i+3] == board[i+6] == mark:
            return True

    if board[0] == board[4] == board[8] == mark:
        return True
    if board[2] == board[4] == board[6] == mark:
        return True

    return False

def play_game():
    current_player = 'X'
    game_over = False

    while not game_over:
        display_board()
        position = int(input("Player " + current_player + ", choose a position (1-9): ")) - 1

        if board[position] == ' ':
            board[position] = current_player

            if check_win(current_player):
                display_board()
                print("Player " + current_player + " wins!")
                game_over = True
            elif ' ' not in board:
                display_board()
                print("It's a tie!")
                game_over = True
            else:
                current_player = 'O' if current_player == 'X' else 'X'
        else:
            print("That position is already occupied. Choose a different position.")

play_game()
