
package config;

interface Views {

static final String VI_AREAS = "SELECT ID_OBJ, S_NAME, MPC_NAME, X, Y, SHIFT_X, SHIFT_Y, VIDEO_STATUS, PRIMARY_IP, SECONDARY_IP, FIRST_RESET_HW, SECOND_RESET_HW, HOLLOW_CANCELING, TRAIN_ROUTING, SHUNTING_ROUITING, HONEST_ODD, IND, CMD, GOT, SN FROM AREA ORDER BY ID_OBJ;";
static final String VI_BEAMS = "SELECT ID_OBJ, COUNT_UNIT_ID, RU, CMD2 FROM beam ORDER BY ID_OBJ;";
static final String VI_BORDERS = "SELECT ID_OBJ, ID_AREA, S_NAME, MPC_NAME, X, Y, SHIFT_X, SHIFT_Y, VIDEO_STATUS, ORIENTATION FROM BORDER ORDER BY ID_OBJ;";
static final String VI_CABINETS = "SELECT CABINET.ID_OBJ, CABINET.ID_AREA, CABINET.RU CABINET_NAME, CABINET.MPC_NAME, CABINET.DE, CABINET.EN, CABINET.racks RACKS_AMOUNT, CABINET.TYPE FROM CABINET, CABINET_TYPE WHERE CABINET.TYPE = CABINET_TYPE.ID_OBJ ORDER BY ID_OBJ;";
static final String VI_CHANNELS = "SELECT CHANNEL.ID_OBJ, UNIT_ID, CHANNEL.LINK_NUMBER, CHANNEL.PURPOSE FROM CHANNEL;";
static final String VI_CMDMESS = "SELECT OBJ_PROP.PROP_VAL, ALARM_TYPE.ID_TYPE FROM OBJ_PROP, ALARM_TYPE WHERE OBJ_PROP = 'C.CTL' AND ALARM_TYPE.TYPE_NAME = OBJ_PROP.INFO_MESS;";
static final String VI_CMDS = "SELECT PROP_VAL, OBJ_TYPE, REG_STR FROM OBJ_PROP WHERE OBJ_PROP = 'C.CTL';";
static final String VI_COLORS = "SELECT ID_OBJ, ID_AREA, S_NANE, MPC_NAME, R, G, B, ID_TYPE FROM COLORS ORDER BY ID_OBJ;";
static final String VI_COM_SERVERS = "SELECT ID_OBJ, AREA FROM COMM_SERVER;";
static final String VI_COUNT_UNITS = "SELECT ID_OBJ, ID_AREA, RU S_NAME, MPC_NAME, DE, EN, IND FROM COUNT_UNIT ORDER BY ID_OBJ;";
static final String VI_COUNTERS = "SELECT ID_OBJ, ID_AREA, S_NAME, MPC_NAME, X, Y, SHIFT_X, SHIFT_Y, VIDEO_STATUS, ORIENTATION, RN1, RN2, INC_SGN, CLRTYPE, BEAM_ID, LOG_ADDRESS, IND FROM COUNTER ORDER BY ID_OBJ;";

//static final String VI_CROSSINGS =
//static final String VI_DEADLOCKS =
//static final String VI_DEFAULTS =
//static final String VI_DGAS =
//static final String VI_DIRECTS = 
//static final String VI_ELEVATEDTRACKS =
//static final String VI_F3_DIO_20_8 = 
//static final String VI_FANS =
//static final String VI_HEATINGS =
//static final String VI_HEATS =
//static final String VI_LIGHTS =
//static final String VI_LOGIN_ERRORS =
//static final String VI_MESS =
//static final String VI_MPABS =
//static final String VI_NEARBYS =
//static final String VI_NOTE_TYPES =
//static final String VI_NOTES =
//static final String VI_OBJ =
//static final String VI_OBJ_TYPES = 
//static final String VI_PARAMS =
//static final String VI_PLCS = 
//static final String VI_PROTECTEDS =
//static final String VI_RAILNET_COUNTERS =
//static final String VI_RAILNETS =
//static final String VI_SIMULATORS =
//static final String VI_TURNOUTS =
//static final String VI_UKSPS =
//static final String VI_UNITS =
//static final String VI_UNRULEDS =
//static final String VI_USERS =
//static final String VI_VCS =
        
}