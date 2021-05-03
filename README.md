# ingswAM2021-Bernardelle-Civelli-Amati



## COMMUNICATION PROTOCOL

| Source      | Message Type  | payload content    |  description
| :----:        |    :----:   |          :----:  |     :----:  |
| Server     | LOGIN_SUCCESFUL       | null    | sent to client when login procedure is succesfull
| Server     | RECONNECTED        | null     | sento to client when he succesfully reconnects to the server
| Server     | ERROR        | [an error](#errors-Description)     | sent to client when an error occurs




### ERRORs Description

| Error Type   |  description  
| :----:        |    :----:   |        
| INVALID_LOGIN_USERNAME     | if username is empty or null
| GAME_ALREADY_STARTED     | if a player tryes to LOGIN when the game is already started
