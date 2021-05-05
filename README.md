# ingswAM2021-Bernardelle-Civelli-Amati



## COMMUNICATION PROTOCOL

| Source      | Message Type  | payload content    |  description
| :----:        |    :----:   |          :----:  |     :----:  |
| Server     | LOGIN_SUCCESFUL       | null    | sent to client when login procedure is succesfull
| Server     | RECONNECTED        | null     | sent to client when he successfully reconnects to the server
| Server     | ERROR        | [an error type](#ERRORs-Description)     | sent to client when an error occurs




### ERRORs Description

| Error Type   |  description  
| :----:        |    :----:   |        
| INVALID_LOGIN_USERNAME     | if username is empty or null
| GAME_ALREADY_STARTED     | if a player tryes to LOGIN when the game is already started
