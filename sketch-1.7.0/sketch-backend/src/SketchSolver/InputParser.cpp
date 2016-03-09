/* A Bison parser, made by GNU Bison 2.3.  */

/* Skeleton implementation for Bison's Yacc-like parsers in C

   Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2, or (at your option)
   any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor,
   Boston, MA 02110-1301, USA.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output.  */
#define YYBISON 1

/* Bison version.  */
#define YYBISON_VERSION "2.3"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 1

/* Using locations.  */
#define YYLSP_NEEDED 0



/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     T_dbl = 258,
     T_int = 259,
     T_bool = 260,
     T_ident = 261,
     T_OutIdent = 262,
     T_NativeCode = 263,
     T_string = 264,
     T_true = 265,
     T_false = 266,
     T_vartype = 267,
     T_rightAC = 268,
     T_leftAC = 269,
     T_rightTC = 270,
     T_leftTC = 271,
     T_leftAR = 272,
     T_rightAR = 273,
     T_arrow = 274,
     T_twoS = 275,
     T_ppls = 276,
     T_mmns = 277,
     T_eq = 278,
     T_neq = 279,
     T_and = 280,
     T_or = 281,
     T_For = 282,
     T_ge = 283,
     T_le = 284,
     T_Native = 285,
     T_NativeMethod = 286,
     T_Sketches = 287,
     T_new = 288,
     T_Typedef = 289,
     T_def = 290,
     T_mdldef = 291,
     T_Min = 292,
     T_sp = 293,
     T_assert = 294,
     T_assume = 295,
     T_hassert = 296,
     T_equals = 297,
     T_replace = 298,
     T_eof = 299
   };
#endif
/* Tokens.  */
#define T_dbl 258
#define T_int 259
#define T_bool 260
#define T_ident 261
#define T_OutIdent 262
#define T_NativeCode 263
#define T_string 264
#define T_true 265
#define T_false 266
#define T_vartype 267
#define T_rightAC 268
#define T_leftAC 269
#define T_rightTC 270
#define T_leftTC 271
#define T_leftAR 272
#define T_rightAR 273
#define T_arrow 274
#define T_twoS 275
#define T_ppls 276
#define T_mmns 277
#define T_eq 278
#define T_neq 279
#define T_and 280
#define T_or 281
#define T_For 282
#define T_ge 283
#define T_le 284
#define T_Native 285
#define T_NativeMethod 286
#define T_Sketches 287
#define T_new 288
#define T_Typedef 289
#define T_def 290
#define T_mdldef 291
#define T_Min 292
#define T_sp 293
#define T_assert 294
#define T_assume 295
#define T_hassert 296
#define T_equals 297
#define T_replace 298
#define T_eof 299




/* Copy the first part of user declarations.  */
#line 1 "InputParser/InputParser.yy"


using namespace std;

BooleanDAGCreator* currentBD;
stack<string> namestack;
vartype Gvartype;
string tupleName;

bool isModel;




#ifdef CONST
#undef CONST
#endif


#define YY_DECL int yylex (YYSTYPE* yylval, yyscan_t yyscanner)
extern int yylex (YYSTYPE* yylval, yyscan_t yyscanner);



/* Enabling traces.  */
#ifndef YYDEBUG
# define YYDEBUG 1
#endif

/* Enabling verbose error messages.  */
#ifdef YYERROR_VERBOSE
# undef YYERROR_VERBOSE
# define YYERROR_VERBOSE 1
#else
# define YYERROR_VERBOSE 0
#endif

/* Enabling the token table.  */
#ifndef YYTOKEN_TABLE
# define YYTOKEN_TABLE 0
#endif

#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE
#line 30 "InputParser/InputParser.yy"
{
	int intConst;
	bool boolConst;
	std::string* strConst;
	double doubleConst;		
	std::list<int>* iList;
	list<bool_node*>* nList;
	list<string*>* sList;
	vartype variableType;
	BooleanDAG* bdag;
	bool_node* bnode;
  OutType* otype;
  vector<OutType*>* tVector;
  vector<string>* sVector;
}
/* Line 193 of yacc.c.  */
#line 224 "InputParser.cpp"
	YYSTYPE;
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
# define YYSTYPE_IS_TRIVIAL 1
#endif



/* Copy the second part of user declarations.  */


/* Line 216 of yacc.c.  */
#line 237 "InputParser.cpp"

#ifdef short
# undef short
#endif

#ifdef YYTYPE_UINT8
typedef YYTYPE_UINT8 yytype_uint8;
#else
typedef unsigned char yytype_uint8;
#endif

#ifdef YYTYPE_INT8
typedef YYTYPE_INT8 yytype_int8;
#elif (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
typedef signed char yytype_int8;
#else
typedef short int yytype_int8;
#endif

#ifdef YYTYPE_UINT16
typedef YYTYPE_UINT16 yytype_uint16;
#else
typedef unsigned short int yytype_uint16;
#endif

#ifdef YYTYPE_INT16
typedef YYTYPE_INT16 yytype_int16;
#else
typedef short int yytype_int16;
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif ! defined YYSIZE_T && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned int
# endif
#endif

#define YYSIZE_MAXIMUM ((YYSIZE_T) -1)

#ifndef YY_
# if defined YYENABLE_NLS && YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(msgid) dgettext ("bison-runtime", msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(msgid) msgid
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YYUSE(e) ((void) (e))
#else
# define YYUSE(e) /* empty */
#endif

/* Identity function, used to suppress warnings about constant conditions.  */
#ifndef lint
# define YYID(n) (n)
#else
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static int
YYID (int i)
#else
static int
YYID (i)
    int i;
#endif
{
  return i;
}
#endif

#if ! defined yyoverflow || YYERROR_VERBOSE

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_USE_ALLOCA
#  if YYSTACK_USE_ALLOCA
#   ifdef __GNUC__
#    define YYSTACK_ALLOC __builtin_alloca
#   elif defined __BUILTIN_VA_ARG_INCR
#    include <alloca.h> /* INFRINGES ON USER NAME SPACE */
#   elif defined _AIX
#    define YYSTACK_ALLOC __alloca
#   elif defined _MSC_VER
#    include <malloc.h> /* INFRINGES ON USER NAME SPACE */
#    define alloca _alloca
#   else
#    define YYSTACK_ALLOC alloca
#    if ! defined _ALLOCA_H && ! defined _STDLIB_H && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#     ifndef _STDLIB_H
#      define _STDLIB_H 1
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's `empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (YYID (0))
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined _STDLIB_H \
       && ! ((defined YYMALLOC || defined malloc) \
	     && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef _STDLIB_H
#    define _STDLIB_H 1
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined _STDLIB_H && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined _STDLIB_H && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* ! defined yyoverflow || YYERROR_VERBOSE */


#if (! defined yyoverflow \
     && (! defined __cplusplus \
	 || (defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yytype_int16 yyss;
  YYSTYPE yyvs;
  };

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (sizeof (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (sizeof (yytype_int16) + sizeof (YYSTYPE)) \
      + YYSTACK_GAP_MAXIMUM)

/* Copy COUNT objects from FROM to TO.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(To, From, Count) \
      __builtin_memcpy (To, From, (Count) * sizeof (*(From)))
#  else
#   define YYCOPY(To, From, Count)		\
      do					\
	{					\
	  YYSIZE_T yyi;				\
	  for (yyi = 0; yyi < (Count); yyi++)	\
	    (To)[yyi] = (From)[yyi];		\
	}					\
      while (YYID (0))
#  endif
# endif

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack)					\
    do									\
      {									\
	YYSIZE_T yynewbytes;						\
	YYCOPY (&yyptr->Stack, Stack, yysize);				\
	Stack = &yyptr->Stack;						\
	yynewbytes = yystacksize * sizeof (*Stack) + YYSTACK_GAP_MAXIMUM; \
	yyptr += yynewbytes / sizeof (*yyptr);				\
      }									\
    while (YYID (0))

#endif

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  5
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   505

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  69
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  36
/* YYNRULES -- Number of rules.  */
#define YYNRULES  133
/* YYNRULES -- Number of states.  */
#define YYNSTATES  348

/* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
#define YYUNDEFTOK  2
#define YYMAXUTOK   299

#define YYTRANSLATE(YYX)						\
  ((unsigned int) (YYX) <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK)

/* YYTRANSLATE[YYLEX] -- Bison symbol number corresponding to YYLEX.  */
static const yytype_uint8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    53,     2,     2,    63,    48,    64,     2,
      57,    58,    46,    45,    56,    68,    67,    47,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,    52,    61,
      49,    62,    50,    51,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,    54,     2,    55,    66,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    59,    65,    60,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43,    44
};

#if YYDEBUG
/* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
   YYRHS.  */
static const yytype_uint16 yyprhs[] =
{
       0,     0,     3,     7,     8,    11,    14,    17,    19,    20,
      24,    26,    29,    32,    35,    41,    45,    49,    56,    63,
      71,    79,    80,    87,    88,    95,   102,   109,   110,   112,
     116,   118,   120,   121,   131,   133,   139,   145,   147,   148,
     151,   157,   162,   168,   169,   172,   173,   178,   189,   193,
     194,   199,   205,   206,   209,   212,   213,   216,   218,   223,
     235,   240,   244,   250,   254,   260,   265,   268,   269,   271,
     275,   279,   283,   287,   291,   295,   299,   304,   310,   315,
     324,   333,   340,   344,   351,   355,   359,   363,   367,   371,
     375,   379,   383,   387,   391,   397,   398,   401,   403,   406,
     408,   410,   427,   443,   446,   449,   453,   455,   459,   464,
     470,   475,   481,   486,   492,   499,   508,   518,   529,   530,
     533,   535,   539,   543,   545,   549,   553,   557,   561,   563,
     566,   568,   570,   572
};

/* YYRHS -- A `-1'-separated list of the rules' RHS.  */
static const yytype_int8 yyrhs[] =
{
      70,     0,    -1,    86,    71,    44,    -1,    -1,    80,    71,
      -1,    89,    71,    -1,    87,    71,    -1,     6,    -1,    -1,
       6,    73,    72,    -1,     6,    -1,     6,    74,    -1,    12,
       6,    -1,     6,     6,    -1,     6,     6,    49,   102,    50,
      -1,    53,    12,     6,    -1,    53,     6,     6,    -1,    12,
      54,    46,   100,    55,     6,    -1,     6,    54,    46,   100,
      55,     6,    -1,    53,    12,    54,    46,   100,    55,     6,
      -1,    53,     6,    54,    46,   100,    55,     6,    -1,    -1,
      12,    54,   100,    55,    76,    72,    -1,    -1,     6,    54,
     100,    55,    77,    72,    -1,    53,    12,    54,   100,    55,
      74,    -1,    53,     6,    54,   100,    55,    74,    -1,    -1,
      75,    -1,    75,    56,    78,    -1,    36,    -1,    35,    -1,
      -1,    79,     6,    81,    57,    78,    58,    59,    92,    60,
      -1,    12,    -1,    12,    54,    46,   100,    55,    -1,     6,
      54,    46,   100,    55,    -1,     6,    -1,    -1,    83,    82,
      -1,    83,    12,    54,   103,    55,    -1,     6,    57,    83,
      58,    -1,     6,    57,   103,    83,    58,    -1,    -1,    85,
      84,    -1,    -1,    34,    59,    85,    60,    -1,    43,     6,
      46,     6,    42,     6,    57,   102,    58,    61,    -1,     6,
      32,     6,    -1,    -1,    39,    90,    88,    61,    -1,     6,
      57,    91,    58,    61,    -1,    -1,     6,    91,    -1,     9,
      91,    -1,    -1,    92,    93,    -1,    61,    -1,     6,    62,
      95,    61,    -1,    63,    97,    20,    96,    63,    54,    95,
      55,    62,    95,    61,    -1,     7,    62,    95,    61,    -1,
      39,    95,    61,    -1,    39,    95,    52,     9,    61,    -1,
      41,    95,    61,    -1,    41,    95,    52,     9,    61,    -1,
      40,    95,    94,    61,    -1,    52,     9,    -1,    -1,    98,
      -1,    98,    64,    98,    -1,    98,    25,    98,    -1,    98,
      65,    98,    -1,    98,    26,    98,    -1,    98,    66,    98,
      -1,    98,    24,    98,    -1,    98,    23,    98,    -1,    98,
      17,    95,    18,    -1,    98,    67,    54,   102,    55,    -1,
     102,    54,    95,    55,    -1,     6,    54,    54,    95,    19,
      95,    55,    55,    -1,   102,    54,    54,    95,    19,    95,
      55,    55,    -1,    63,    96,    63,    54,    95,    55,    -1,
      14,    96,    13,    -1,    54,     6,    55,    16,    96,    15,
      -1,    20,    96,    20,    -1,    98,    45,    98,    -1,    98,
      47,    98,    -1,    98,    48,    98,    -1,    98,    46,    98,
      -1,    98,    68,    98,    -1,    98,    50,    98,    -1,    98,
      49,    98,    -1,    98,    28,    98,    -1,    98,    29,    98,
      -1,    95,    51,    95,    52,    95,    -1,    -1,    98,    96,
      -1,     6,    -1,     6,    97,    -1,   103,    -1,     3,    -1,
       6,    54,    46,     6,    55,    57,    96,    58,    57,    95,
      58,    54,     6,    56,   103,    55,    -1,     6,    54,    12,
      55,    57,    96,    58,    57,    95,    58,    54,     6,    56,
     103,    55,    -1,    68,    98,    -1,    53,    98,    -1,    57,
      95,    58,    -1,   104,    -1,    49,   104,    50,    -1,    49,
     104,   103,    50,    -1,    49,   104,   103,    46,    50,    -1,
      49,   104,    45,    50,    -1,    49,   104,   103,    45,    50,
      -1,    37,    49,   104,    50,    -1,    37,    49,   104,   103,
      50,    -1,    37,    49,   104,   103,    46,    50,    -1,    38,
     103,    63,    99,    63,    49,   104,    50,    -1,    38,   103,
      63,    99,    63,    49,   104,   103,    50,    -1,    38,   103,
      63,    99,    63,    49,   104,   103,    46,    50,    -1,    -1,
      99,   104,    -1,   101,    -1,   100,    45,   101,    -1,   100,
      68,   101,    -1,   102,    -1,    57,   101,    58,    -1,   101,
      46,   101,    -1,   101,    47,   101,    -1,   101,    48,   101,
      -1,   103,    -1,    68,   103,    -1,     4,    -1,    10,    -1,
      11,    -1,     6,    -1
};

/* YYRLINE[YYN] -- source line where rule number YYN was defined.  */
static const yytype_uint16 yyrline[] =
{
       0,   127,   127,   130,   131,   132,   133,   136,   152,   152,
     168,   169,   175,   188,   195,   202,   212,   218,   232,   236,
     245,   248,   248,   249,   249,   250,   251,   254,   255,   256,
     258,   258,   261,   260,   277,   284,   289,   292,   296,   297,
     301,   313,   318,   322,   323,   325,   326,   328,   333,   342,
     342,   354,   364,   367,   371,   377,   378,   381,   382,   386,
     414,   419,   426,   437,   446,   459,   476,   477,   479,   480,
     483,   486,   489,   492,   495,   499,   502,   505,   513,   516,
     523,   529,   543,   557,   572,   585,   589,   593,   597,   600,
     604,   608,   611,   615,   619,   630,   631,   642,   646,   651,
     654,   658,   693,   741,   744,   748,   751,   756,   760,   768,
     773,   777,   781,   785,   793,   798,   803,   812,   819,   820,
     825,   826,   827,   829,   830,   831,   832,   834,   838,   839,
     842,   843,   844,   846
};
#endif

#if YYDEBUG || YYERROR_VERBOSE || YYTOKEN_TABLE
/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "$end", "error", "$undefined", "T_dbl", "T_int", "T_bool", "T_ident",
  "T_OutIdent", "T_NativeCode", "T_string", "T_true", "T_false",
  "T_vartype", "T_rightAC", "T_leftAC", "T_rightTC", "T_leftTC",
  "T_leftAR", "T_rightAR", "T_arrow", "T_twoS", "T_ppls", "T_mmns", "T_eq",
  "T_neq", "T_and", "T_or", "T_For", "T_ge", "T_le", "T_Native",
  "T_NativeMethod", "T_Sketches", "T_new", "T_Typedef", "T_def",
  "T_mdldef", "T_Min", "T_sp", "T_assert", "T_assume", "T_hassert",
  "T_equals", "T_replace", "T_eof", "'+'", "'*'", "'/'", "'%'", "'<'",
  "'>'", "'?'", "':'", "'!'", "'['", "']'", "','", "'('", "')'", "'{'",
  "'}'", "';'", "'='", "'$'", "'&'", "'|'", "'^'", "'.'", "'-'", "$accept",
  "Program", "MethodList", "InList", "@1", "OutList", "ParamDecl", "@2",
  "@3", "ParamList", "Mhelp", "Method", "@4", "TupleType", "TupleTypeList",
  "TypeLine", "TypeList", "Typedef", "Replacement", "AssertionExpr",
  "HLAssertion", "@5", "TokenList", "WorkBody", "WorkStatement",
  "OptionalMsg", "Expression", "varList", "IdentList", "Term",
  "ParentsList", "ConstantExpr", "ConstantTerm", "NegConstant", "Constant",
  "Ident", 0
};
#endif

# ifdef YYPRINT
/* YYTOKNUM[YYLEX-NUM] -- Internal token number corresponding to
   token YYLEX-NUM.  */
static const yytype_uint16 yytoknum[] =
{
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,    43,    42,    47,    37,    60,
      62,    63,    58,    33,    91,    93,    44,    40,    41,   123,
     125,    59,    61,    36,    38,   124,    94,    46,    45
};
# endif

/* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
static const yytype_uint8 yyr1[] =
{
       0,    69,    70,    71,    71,    71,    71,    72,    73,    72,
      74,    74,    75,    75,    75,    75,    75,    75,    75,    75,
      75,    76,    75,    77,    75,    75,    75,    78,    78,    78,
      79,    79,    81,    80,    82,    82,    82,    82,    83,    83,
      83,    84,    84,    85,    85,    86,    86,    87,    88,    90,
      89,    89,    91,    91,    91,    92,    92,    93,    93,    93,
      93,    93,    93,    93,    93,    93,    94,    94,    95,    95,
      95,    95,    95,    95,    95,    95,    95,    95,    95,    95,
      95,    95,    95,    95,    95,    95,    95,    95,    95,    95,
      95,    95,    95,    95,    95,    96,    96,    97,    97,    98,
      98,    98,    98,    98,    98,    98,    98,    98,    98,    98,
      98,    98,    98,    98,    98,    98,    98,    98,    99,    99,
     100,   100,   100,   101,   101,   101,   101,   101,   102,   102,
     103,   103,   103,   104
};

/* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
static const yytype_uint8 yyr2[] =
{
       0,     2,     3,     0,     2,     2,     2,     1,     0,     3,
       1,     2,     2,     2,     5,     3,     3,     6,     6,     7,
       7,     0,     6,     0,     6,     6,     6,     0,     1,     3,
       1,     1,     0,     9,     1,     5,     5,     1,     0,     2,
       5,     4,     5,     0,     2,     0,     4,    10,     3,     0,
       4,     5,     0,     2,     2,     0,     2,     1,     4,    11,
       4,     3,     5,     3,     5,     4,     2,     0,     1,     3,
       3,     3,     3,     3,     3,     3,     4,     5,     4,     8,
       8,     6,     3,     6,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     5,     0,     2,     1,     2,     1,
       1,    16,    15,     2,     2,     3,     1,     3,     4,     5,
       4,     5,     4,     5,     6,     8,     9,    10,     0,     2,
       1,     3,     3,     1,     3,     3,     3,     3,     1,     2,
       1,     1,     1,     1
};

/* YYDEFACT[STATE-NAME] -- Default rule to reduce with in state
   STATE-NUM when YYTABLE doesn't specify something else to do.  Zero
   means the default is an error.  */
static const yytype_uint8 yydefact[] =
{
      45,     0,     0,     3,    43,     1,     0,    31,    30,    49,
       0,     0,     0,     3,     3,     3,     0,    52,     0,     0,
       2,    32,     4,     6,     5,     0,    46,    44,    52,    52,
       0,     0,     0,     0,     0,    38,    53,    54,     0,     0,
      50,     0,    27,   130,   131,   132,     0,    38,    51,    48,
       0,     0,     0,     0,    28,     0,    37,    34,    41,    39,
       0,     0,    13,     0,    12,     0,     0,     0,    27,     0,
       0,     0,    42,     0,     0,     0,     0,     0,     0,   120,
     123,   128,     0,     0,    16,     0,    15,     0,    29,    55,
       0,     0,     0,     0,     0,     0,     0,   129,     0,    23,
       0,     0,     0,     0,     0,    21,     0,     0,     0,     0,
       0,     0,     0,    40,     0,    14,     0,   124,   121,     0,
     122,   125,   126,   127,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,    33,    57,     0,    56,    36,
      35,    47,    18,     7,    24,    17,    22,     0,    10,    26,
       0,    25,     0,     0,   100,   133,    95,    95,     0,     0,
       0,     0,     0,     0,    95,     0,     0,    68,     0,    99,
     106,    67,     0,    97,     0,     0,    20,    11,    19,     0,
       0,     0,   133,     0,     0,    95,    99,     0,     0,     0,
     133,     0,   104,     0,     0,     0,   103,    99,     0,     0,
      61,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    63,    98,    95,     9,    58,    60,     0,
       0,     0,     0,    82,    96,    84,     0,   118,     0,   107,
       0,     0,   105,     0,     0,     0,     0,    75,    74,    70,
      72,    92,    93,    85,    88,    86,    87,    91,    90,    69,
      71,    73,     0,    89,     0,     0,    66,    65,     0,     0,
       0,     0,     0,   112,     0,     0,   110,     0,     0,   108,
      95,     0,     0,    62,    76,     0,   133,     0,    78,    64,
       0,    95,     0,     0,     0,   113,     0,   119,   111,   109,
       0,     0,    94,    77,     0,     0,     0,    95,     0,   114,
       0,    83,    81,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    79,   115,     0,    80,     0,     0,     0,
       0,   116,     0,     0,     0,   117,    59,     0,     0,     0,
       0,     0,     0,     0,     0,   102,     0,   101
};

/* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int16 yydefgoto[] =
{
      -1,     2,    11,   144,   175,   149,    54,   125,   119,    55,
      12,    13,    34,    59,    46,    27,    16,     3,    14,    32,
      15,    18,    30,   110,   138,   221,   166,   184,   174,   167,
     275,    78,    79,   168,   186,   170
};

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
#define YYPACT_NINF -160
static const yytype_int16 yypact[] =
{
      80,    73,   123,   159,  -160,  -160,    84,  -160,  -160,  -160,
     119,   106,   163,   159,   159,   159,     9,   259,   169,   143,
    -160,  -160,  -160,  -160,  -160,   146,  -160,  -160,   259,   259,
     147,   187,   195,   218,   223,   212,  -160,  -160,   229,   290,
    -160,   257,    83,  -160,  -160,  -160,    -4,  -160,  -160,  -160,
     302,    -1,    21,    12,   254,   272,   283,   297,  -160,  -160,
      19,   288,   307,     2,  -160,    33,    28,    29,    83,   323,
     318,   202,  -160,     6,     6,    96,    96,   212,   -22,   -26,
    -160,  -160,    96,    66,  -160,    47,  -160,    74,  -160,  -160,
      96,    96,   311,   313,   336,    94,   206,  -160,    96,  -160,
      96,    96,    96,    96,   173,  -160,    96,   205,    96,   236,
     237,   284,   293,  -160,   327,  -160,   383,  -160,   -26,   385,
     -26,   188,   346,  -160,   393,   385,   310,   396,   340,   396,
     345,   350,   177,   177,   177,  -160,  -160,   399,  -160,  -160,
    -160,  -160,  -160,   408,  -160,  -160,  -160,   410,   396,  -160,
     414,  -160,   177,   177,  -160,   367,   366,   366,   373,   212,
     422,   366,   425,   177,   366,   366,    61,   401,   378,   379,
    -160,   250,   156,   399,   416,   385,  -160,  -160,  -160,   -42,
     116,   128,   387,   366,   429,   366,  -160,   423,   422,   382,
    -160,   175,  -160,   389,    36,   390,  -160,   398,   177,   445,
    -160,   177,   366,   366,   366,   366,   366,   366,   366,   366,
     366,   366,   366,   366,   366,   366,   366,   402,   366,   330,
     446,   397,   448,  -160,  -160,   366,  -160,  -160,  -160,   404,
     454,   177,    64,  -160,  -160,  -160,    98,  -160,   411,  -160,
     192,   447,  -160,   417,   291,   403,    68,  -160,  -160,  -160,
    -160,  -160,  -160,  -160,  -160,  -160,  -160,  -160,  -160,  -160,
    -160,  -160,     6,  -160,   343,    -6,  -160,  -160,   409,   412,
     405,   418,    86,  -160,    42,    -3,  -160,   424,   426,  -160,
     366,   177,   177,  -160,  -160,   427,   305,   139,  -160,  -160,
     430,   366,   415,   177,   428,  -160,   431,  -160,  -160,  -160,
     462,   208,  -160,  -160,   177,   177,   421,   366,   234,  -160,
     422,  -160,  -160,   256,   276,   432,   433,   435,   189,   437,
     419,   177,   436,  -160,  -160,   312,  -160,   177,    45,   177,
     438,  -160,   200,   440,   153,  -160,  -160,   477,   441,   442,
     479,   212,   443,   449,   212,  -160,   450,  -160
};

/* YYPGOTO[NTERM-NUM].  */
static const yytype_int16 yypgoto[] =
{
    -160,  -160,   360,  -111,  -160,  -122,  -160,  -160,  -160,   434,
    -160,  -160,  -160,  -160,   439,  -160,  -160,  -160,  -160,  -160,
    -160,  -160,   381,  -160,  -160,  -160,   -72,  -153,   314,   110,
    -160,   197,   337,    70,   -35,  -159
};

/* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule which
   number is the opposite.  If zero, do what YYDEFACT says.
   If YYTABLE_NINF, syntax error.  */
#define YYTABLE_NINF -130
static const yytype_int16 yytable[] =
{
      47,   191,    56,   190,   187,    62,    43,   151,    57,   198,
      43,   195,    44,    45,   146,    25,    44,    45,    66,   227,
     101,   102,   103,    98,    67,    56,   177,    64,    81,   236,
      81,    57,   234,    99,    84,    86,    92,    43,    81,    81,
      81,    81,    97,    44,    45,   198,   100,    81,    75,   288,
      81,    43,    81,    63,    58,    81,    81,    44,    45,    76,
     296,   171,   172,    81,   226,    81,    81,    81,    81,    26,
      77,    81,   269,    81,    77,    65,   229,    72,    43,    82,
     179,   180,    85,    87,    44,    45,   284,   198,   294,    51,
      76,   194,   295,   106,   242,    52,   198,   169,   169,   169,
      43,    77,    43,   333,    76,   293,    44,    45,    44,    45,
     230,    98,   198,   199,     1,    77,   297,   169,   169,   198,
     108,   105,   200,     5,   189,    19,   244,   300,   169,   246,
     197,    76,     4,    80,   100,    80,    53,   198,   306,    98,
     229,    17,    77,    93,    94,    80,    80,   265,   273,   116,
      20,   318,    80,    76,   316,    80,   240,    80,   304,   272,
      80,    80,   100,   169,    77,     6,   169,   198,    80,    21,
      80,    80,    80,    80,   230,    31,    80,   228,    80,    43,
     154,    43,   231,   155,   169,    44,    45,    44,    45,    33,
     198,   156,   287,    43,     7,     8,   169,   157,     9,    44,
      45,   274,    10,    35,   198,    38,    43,   198,   222,   301,
     302,   338,    44,    45,   158,   159,    43,   223,    98,    39,
     238,   308,    44,    45,    41,   239,   160,    81,   124,   169,
     161,   162,   313,   314,   163,   102,   103,   277,   278,   324,
     164,   100,   279,   130,   131,   165,   169,   169,    91,   328,
      98,   198,   101,   102,   103,   332,    40,   334,   169,   198,
     127,   336,    83,   312,   117,    28,   185,   185,    29,   169,
     169,   192,    95,   100,   185,   196,   132,   133,   134,   104,
      42,    98,   107,   325,   109,   198,   169,   111,   112,   317,
      48,   129,   169,   196,   169,   185,    49,   135,   136,    50,
     137,   198,   220,   126,   100,   128,   343,   198,    61,   346,
      68,   319,   247,   248,   249,   250,   251,   252,   253,   254,
     255,   256,   257,   258,   259,   260,   261,   198,   263,    98,
      69,   320,   285,   154,    43,   185,   155,    70,    98,   139,
      44,    45,   198,   282,   156,    73,   154,    43,   140,   286,
     157,    71,   100,    44,    45,    98,    74,   156,   330,   181,
     241,   100,   331,   157,    90,   147,   113,   158,   159,   154,
      43,   114,   182,    22,    23,    24,    44,    45,   100,   160,
     158,   159,    89,   161,   264,    98,   115,   163,   141,   142,
     185,   143,   160,   164,   103,   150,   161,   162,   165,   145,
     163,   185,   148,   158,   159,   173,   164,   152,   100,    36,
      37,   165,   153,    96,    -8,   160,   176,   185,   201,   161,
     178,   181,   188,   163,   202,   203,   204,   205,   190,   206,
     207,   193,   219,  -128,   183,   118,   225,   120,   121,   122,
     123,   232,   233,   235,   241,   237,   208,   209,   210,   211,
     212,   213,  -129,   243,   245,   266,   262,   268,   267,   270,
     271,   276,   291,   280,   283,   214,   215,   216,   217,   218,
     289,   281,   307,   292,   298,   290,   299,   311,   309,   315,
     310,   327,   303,   339,   305,   342,    60,   224,   335,   321,
     323,   322,   326,   329,   337,   340,     0,     0,   341,   344,
       0,     0,    88,     0,   345,   347
};

static const yytype_int16 yycheck[] =
{
      35,   160,     6,     6,   157,     6,     4,   129,    12,    51,
       4,   164,    10,    11,   125,     6,    10,    11,     6,    61,
      46,    47,    48,    45,    12,     6,   148,     6,    63,   188,
      65,    12,   185,    55,     6,     6,    71,     4,    73,    74,
      75,    76,    77,    10,    11,    51,    68,    82,    46,    55,
      85,     4,    87,    54,    58,    90,    91,    10,    11,    57,
      63,   133,   134,    98,   175,   100,   101,   102,   103,    60,
      68,   106,   225,   108,    68,    54,    12,    58,     4,    46,
     152,   153,    54,    54,    10,    11,    18,    51,    46,     6,
      57,   163,    50,    46,    58,    12,    51,   132,   133,   134,
       4,    68,     4,    58,    57,    19,    10,    11,    10,    11,
      46,    45,    51,    52,    34,    68,   275,   152,   153,    51,
      46,    55,    61,     0,   159,     6,   198,   280,   163,   201,
     165,    57,    59,    63,    68,    65,    53,    51,   291,    45,
      12,    57,    68,    73,    74,    75,    76,   219,    50,    55,
      44,   310,    82,    57,   307,    85,   191,    87,    19,   231,
      90,    91,    68,   198,    68,     6,   201,    51,    98,     6,
     100,   101,   102,   103,    46,     6,   106,    61,   108,     4,
       3,     4,    54,     6,   219,    10,    11,    10,    11,    46,
      51,    14,   264,     4,    35,    36,   231,    20,    39,    10,
      11,   236,    43,    57,    51,    58,     4,    51,    52,   281,
     282,    58,    10,    11,    37,    38,     4,    61,    45,    32,
      45,   293,    10,    11,     6,    50,    49,   262,    55,   264,
      53,    54,   304,   305,    57,    47,    48,    45,    46,    50,
      63,    68,    50,     6,     7,    68,   281,   282,    46,   321,
      45,    51,    46,    47,    48,   327,    61,   329,   293,    51,
      55,    61,    65,    55,    58,     6,   156,   157,     9,   304,
     305,   161,    75,    68,   164,   165,    39,    40,    41,    82,
      57,    45,    85,   318,    87,    51,   321,    90,    91,    55,
      61,    55,   327,   183,   329,   185,     6,    60,    61,    42,
      63,    51,    52,   106,    68,   108,   341,    51,     6,   344,
      56,    55,   202,   203,   204,   205,   206,   207,   208,   209,
     210,   211,   212,   213,   214,   215,   216,    51,   218,    45,
      58,    55,   262,     3,     4,   225,     6,    54,    45,    55,
      10,    11,    51,    52,    14,    57,     3,     4,    55,     6,
      20,    54,    68,    10,    11,    45,    49,    14,    46,    54,
      55,    68,    50,    20,    46,    55,    55,    37,    38,     3,
       4,    58,     6,    13,    14,    15,    10,    11,    68,    49,
      37,    38,    59,    53,    54,    45,    50,    57,    61,     6,
     280,     6,    49,    63,    48,    55,    53,    54,    68,     6,
      57,   291,     6,    37,    38,     6,    63,    62,    68,    28,
      29,    68,    62,    76,     6,    49,     6,   307,    17,    53,
       6,    54,    49,    57,    23,    24,    25,    26,     6,    28,
      29,     6,    54,    54,    68,    98,    20,   100,   101,   102,
     103,    54,    13,    20,    55,    63,    45,    46,    47,    48,
      49,    50,    54,    63,     9,     9,    54,     9,    61,    55,
       6,    50,    57,    16,    61,    64,    65,    66,    67,    68,
      61,    54,    57,    55,    50,    63,    50,    15,    50,    58,
      49,    62,    55,     6,    54,     6,    47,   173,    50,    57,
      55,    58,    55,    57,    54,    54,    -1,    -1,    56,    56,
      -1,    -1,    68,    -1,    55,    55
};

/* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
   symbol of state STATE-NUM.  */
static const yytype_uint8 yystos[] =
{
       0,    34,    70,    86,    59,     0,     6,    35,    36,    39,
      43,    71,    79,    80,    87,    89,    85,    57,    90,     6,
      44,     6,    71,    71,    71,     6,    60,    84,     6,     9,
      91,     6,    88,    46,    81,    57,    91,    91,    58,    32,
      61,     6,    57,     4,    10,    11,    83,   103,    61,     6,
      42,     6,    12,    53,    75,    78,     6,    12,    58,    82,
      83,     6,     6,    54,     6,    54,     6,    12,    56,    58,
      54,    54,    58,    57,    49,    46,    57,    68,   100,   101,
     102,   103,    46,   100,     6,    54,     6,    54,    78,    59,
      46,    46,   103,   102,   102,   100,   101,   103,    45,    55,
      68,    46,    47,    48,   100,    55,    46,   100,    46,   100,
      92,   100,   100,    55,    58,    50,    55,    58,   101,    77,
     101,   101,   101,   101,    55,    76,   100,    55,   100,    55,
       6,     7,    39,    40,    41,    60,    61,    63,    93,    55,
      55,    61,     6,     6,    72,     6,    72,    55,     6,    74,
      55,    74,    62,    62,     3,     6,    14,    20,    37,    38,
      49,    53,    54,    57,    63,    68,    95,    98,   102,   103,
     104,    95,    95,     6,    97,    73,     6,    74,     6,    95,
      95,    54,     6,    68,    96,    98,   103,    96,    49,   103,
       6,   104,    98,     6,    95,    96,    98,   103,    51,    52,
      61,    17,    23,    24,    25,    26,    28,    29,    45,    46,
      47,    48,    49,    50,    64,    65,    66,    67,    68,    54,
      52,    94,    52,    61,    97,    20,    72,    61,    61,    12,
      46,    54,    54,    13,    96,    20,   104,    63,    45,    50,
     103,    55,    58,    63,    95,     9,    95,    98,    98,    98,
      98,    98,    98,    98,    98,    98,    98,    98,    98,    98,
      98,    98,    54,    98,    54,    95,     9,    61,     9,    96,
      55,     6,    95,    50,   103,    99,    50,    45,    46,    50,
      16,    54,    52,    61,    18,   102,     6,    95,    55,    61,
      63,    57,    55,    19,    46,    50,    63,   104,    50,    50,
      96,    95,    95,    55,    19,    54,    96,    57,    95,    50,
      49,    15,    55,    95,    95,    58,    96,    55,   104,    55,
      55,    57,    58,    55,    50,   103,    55,    62,    95,    57,
      46,    50,    95,    58,    95,    50,    61,    54,    58,     6,
      54,    56,     6,   103,    56,    55,   103,    55
};

#define yyerrok		(yyerrstatus = 0)
#define yyclearin	(yychar = YYEMPTY)
#define YYEMPTY		(-2)
#define YYEOF		0

#define YYACCEPT	goto yyacceptlab
#define YYABORT		goto yyabortlab
#define YYERROR		goto yyerrorlab


/* Like YYERROR except do call yyerror.  This remains here temporarily
   to ease the transition to the new meaning of YYERROR, for GCC.
   Once GCC version 2 has supplanted version 1, this can go.  */

#define YYFAIL		goto yyerrlab

#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)					\
do								\
  if (yychar == YYEMPTY && yylen == 1)				\
    {								\
      yychar = (Token);						\
      yylval = (Value);						\
      yytoken = YYTRANSLATE (yychar);				\
      YYPOPSTACK (1);						\
      goto yybackup;						\
    }								\
  else								\
    {								\
      yyerror (yyscanner, YY_("syntax error: cannot back up")); \
      YYERROR;							\
    }								\
while (YYID (0))


#define YYTERROR	1
#define YYERRCODE	256


/* YYLLOC_DEFAULT -- Set CURRENT to span from RHS[1] to RHS[N].
   If N is 0, then set CURRENT to the empty location which ends
   the previous symbol: RHS[0] (always defined).  */

#define YYRHSLOC(Rhs, K) ((Rhs)[K])
#ifndef YYLLOC_DEFAULT
# define YYLLOC_DEFAULT(Current, Rhs, N)				\
    do									\
      if (YYID (N))                                                    \
	{								\
	  (Current).first_line   = YYRHSLOC (Rhs, 1).first_line;	\
	  (Current).first_column = YYRHSLOC (Rhs, 1).first_column;	\
	  (Current).last_line    = YYRHSLOC (Rhs, N).last_line;		\
	  (Current).last_column  = YYRHSLOC (Rhs, N).last_column;	\
	}								\
      else								\
	{								\
	  (Current).first_line   = (Current).last_line   =		\
	    YYRHSLOC (Rhs, 0).last_line;				\
	  (Current).first_column = (Current).last_column =		\
	    YYRHSLOC (Rhs, 0).last_column;				\
	}								\
    while (YYID (0))
#endif


/* YY_LOCATION_PRINT -- Print the location on the stream.
   This macro was not mandated originally: define only if we know
   we won't break user code: when these are the locations we know.  */

#ifndef YY_LOCATION_PRINT
# if defined YYLTYPE_IS_TRIVIAL && YYLTYPE_IS_TRIVIAL
#  define YY_LOCATION_PRINT(File, Loc)			\
     fprintf (File, "%d.%d-%d.%d",			\
	      (Loc).first_line, (Loc).first_column,	\
	      (Loc).last_line,  (Loc).last_column)
# else
#  define YY_LOCATION_PRINT(File, Loc) ((void) 0)
# endif
#endif


/* YYLEX -- calling `yylex' with the right arguments.  */

#ifdef YYLEX_PARAM
# define YYLEX yylex (&yylval, YYLEX_PARAM)
#else
# define YYLEX yylex (&yylval, yyscanner)
#endif

/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)			\
do {						\
  if (yydebug)					\
    YYFPRINTF Args;				\
} while (YYID (0))

# define YY_SYMBOL_PRINT(Title, Type, Value, Location)			  \
do {									  \
  if (yydebug)								  \
    {									  \
      YYFPRINTF (stderr, "%s ", Title);					  \
      yy_symbol_print (stderr,						  \
		  Type, Value, yyscanner); \
      YYFPRINTF (stderr, "\n");						  \
    }									  \
} while (YYID (0))


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

/*ARGSUSED*/
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_symbol_value_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep, yyscan_t yyscanner)
#else
static void
yy_symbol_value_print (yyoutput, yytype, yyvaluep, yyscanner)
    FILE *yyoutput;
    int yytype;
    YYSTYPE const * const yyvaluep;
    yyscan_t yyscanner;
#endif
{
  if (!yyvaluep)
    return;
  YYUSE (yyscanner);
# ifdef YYPRINT
  if (yytype < YYNTOKENS)
    YYPRINT (yyoutput, yytoknum[yytype], *yyvaluep);
# else
  YYUSE (yyoutput);
# endif
  switch (yytype)
    {
      default:
	break;
    }
}


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_symbol_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep, yyscan_t yyscanner)
#else
static void
yy_symbol_print (yyoutput, yytype, yyvaluep, yyscanner)
    FILE *yyoutput;
    int yytype;
    YYSTYPE const * const yyvaluep;
    yyscan_t yyscanner;
#endif
{
  if (yytype < YYNTOKENS)
    YYFPRINTF (yyoutput, "token %s (", yytname[yytype]);
  else
    YYFPRINTF (yyoutput, "nterm %s (", yytname[yytype]);

  yy_symbol_value_print (yyoutput, yytype, yyvaluep, yyscanner);
  YYFPRINTF (yyoutput, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_stack_print (yytype_int16 *bottom, yytype_int16 *top)
#else
static void
yy_stack_print (bottom, top)
    yytype_int16 *bottom;
    yytype_int16 *top;
#endif
{
  YYFPRINTF (stderr, "Stack now");
  for (; bottom <= top; ++bottom)
    YYFPRINTF (stderr, " %d", *bottom);
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)				\
do {								\
  if (yydebug)							\
    yy_stack_print ((Bottom), (Top));				\
} while (YYID (0))


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_reduce_print (YYSTYPE *yyvsp, int yyrule, yyscan_t yyscanner)
#else
static void
yy_reduce_print (yyvsp, yyrule, yyscanner)
    YYSTYPE *yyvsp;
    int yyrule;
    yyscan_t yyscanner;
#endif
{
  int yynrhs = yyr2[yyrule];
  int yyi;
  unsigned long int yylno = yyrline[yyrule];
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %lu):\n",
	     yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      fprintf (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr, yyrhs[yyprhs[yyrule] + yyi],
		       &(yyvsp[(yyi + 1) - (yynrhs)])
		       		       , yyscanner);
      fprintf (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)		\
do {					\
  if (yydebug)				\
    yy_reduce_print (yyvsp, Rule, yyscanner); \
} while (YYID (0))

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args)
# define YY_SYMBOL_PRINT(Title, Type, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef	YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif



#if YYERROR_VERBOSE

# ifndef yystrlen
#  if defined __GLIBC__ && defined _STRING_H
#   define yystrlen strlen
#  else
/* Return the length of YYSTR.  */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static YYSIZE_T
yystrlen (const char *yystr)
#else
static YYSIZE_T
yystrlen (yystr)
    const char *yystr;
#endif
{
  YYSIZE_T yylen;
  for (yylen = 0; yystr[yylen]; yylen++)
    continue;
  return yylen;
}
#  endif
# endif

# ifndef yystpcpy
#  if defined __GLIBC__ && defined _STRING_H && defined _GNU_SOURCE
#   define yystpcpy stpcpy
#  else
/* Copy YYSRC to YYDEST, returning the address of the terminating '\0' in
   YYDEST.  */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static char *
yystpcpy (char *yydest, const char *yysrc)
#else
static char *
yystpcpy (yydest, yysrc)
    char *yydest;
    const char *yysrc;
#endif
{
  char *yyd = yydest;
  const char *yys = yysrc;

  while ((*yyd++ = *yys++) != '\0')
    continue;

  return yyd - 1;
}
#  endif
# endif

# ifndef yytnamerr
/* Copy to YYRES the contents of YYSTR after stripping away unnecessary
   quotes and backslashes, so that it's suitable for yyerror.  The
   heuristic is that double-quoting is unnecessary unless the string
   contains an apostrophe, a comma, or backslash (other than
   backslash-backslash).  YYSTR is taken from yytname.  If YYRES is
   null, do not copy; instead, return the length of what the result
   would have been.  */
static YYSIZE_T
yytnamerr (char *yyres, const char *yystr)
{
  if (*yystr == '"')
    {
      YYSIZE_T yyn = 0;
      char const *yyp = yystr;

      for (;;)
	switch (*++yyp)
	  {
	  case '\'':
	  case ',':
	    goto do_not_strip_quotes;

	  case '\\':
	    if (*++yyp != '\\')
	      goto do_not_strip_quotes;
	    /* Fall through.  */
	  default:
	    if (yyres)
	      yyres[yyn] = *yyp;
	    yyn++;
	    break;

	  case '"':
	    if (yyres)
	      yyres[yyn] = '\0';
	    return yyn;
	  }
    do_not_strip_quotes: ;
    }

  if (! yyres)
    return yystrlen (yystr);

  return yystpcpy (yyres, yystr) - yyres;
}
# endif

/* Copy into YYRESULT an error message about the unexpected token
   YYCHAR while in state YYSTATE.  Return the number of bytes copied,
   including the terminating null byte.  If YYRESULT is null, do not
   copy anything; just return the number of bytes that would be
   copied.  As a special case, return 0 if an ordinary "syntax error"
   message will do.  Return YYSIZE_MAXIMUM if overflow occurs during
   size calculation.  */
static YYSIZE_T
yysyntax_error (char *yyresult, int yystate, int yychar)
{
  int yyn = yypact[yystate];

  if (! (YYPACT_NINF < yyn && yyn <= YYLAST))
    return 0;
  else
    {
      int yytype = YYTRANSLATE (yychar);
      YYSIZE_T yysize0 = yytnamerr (0, yytname[yytype]);
      YYSIZE_T yysize = yysize0;
      YYSIZE_T yysize1;
      int yysize_overflow = 0;
      enum { YYERROR_VERBOSE_ARGS_MAXIMUM = 5 };
      char const *yyarg[YYERROR_VERBOSE_ARGS_MAXIMUM];
      int yyx;

# if 0
      /* This is so xgettext sees the translatable formats that are
	 constructed on the fly.  */
      YY_("syntax error, unexpected %s");
      YY_("syntax error, unexpected %s, expecting %s");
      YY_("syntax error, unexpected %s, expecting %s or %s");
      YY_("syntax error, unexpected %s, expecting %s or %s or %s");
      YY_("syntax error, unexpected %s, expecting %s or %s or %s or %s");
# endif
      char *yyfmt;
      char const *yyf;
      static char const yyunexpected[] = "syntax error, unexpected %s";
      static char const yyexpecting[] = ", expecting %s";
      static char const yyor[] = " or %s";
      char yyformat[sizeof yyunexpected
		    + sizeof yyexpecting - 1
		    + ((YYERROR_VERBOSE_ARGS_MAXIMUM - 2)
		       * (sizeof yyor - 1))];
      char const *yyprefix = yyexpecting;

      /* Start YYX at -YYN if negative to avoid negative indexes in
	 YYCHECK.  */
      int yyxbegin = yyn < 0 ? -yyn : 0;

      /* Stay within bounds of both yycheck and yytname.  */
      int yychecklim = YYLAST - yyn + 1;
      int yyxend = yychecklim < YYNTOKENS ? yychecklim : YYNTOKENS;
      int yycount = 1;

      yyarg[0] = yytname[yytype];
      yyfmt = yystpcpy (yyformat, yyunexpected);

      for (yyx = yyxbegin; yyx < yyxend; ++yyx)
	if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR)
	  {
	    if (yycount == YYERROR_VERBOSE_ARGS_MAXIMUM)
	      {
		yycount = 1;
		yysize = yysize0;
		yyformat[sizeof yyunexpected - 1] = '\0';
		break;
	      }
	    yyarg[yycount++] = yytname[yyx];
	    yysize1 = yysize + yytnamerr (0, yytname[yyx]);
	    yysize_overflow |= (yysize1 < yysize);
	    yysize = yysize1;
	    yyfmt = yystpcpy (yyfmt, yyprefix);
	    yyprefix = yyor;
	  }

      yyf = YY_(yyformat);
      yysize1 = yysize + yystrlen (yyf);
      yysize_overflow |= (yysize1 < yysize);
      yysize = yysize1;

      if (yysize_overflow)
	return YYSIZE_MAXIMUM;

      if (yyresult)
	{
	  /* Avoid sprintf, as that infringes on the user's name space.
	     Don't have undefined behavior even if the translation
	     produced a string with the wrong number of "%s"s.  */
	  char *yyp = yyresult;
	  int yyi = 0;
	  while ((*yyp = *yyf) != '\0')
	    {
	      if (*yyp == '%' && yyf[1] == 's' && yyi < yycount)
		{
		  yyp += yytnamerr (yyp, yyarg[yyi++]);
		  yyf += 2;
		}
	      else
		{
		  yyp++;
		  yyf++;
		}
	    }
	}
      return yysize;
    }
}
#endif /* YYERROR_VERBOSE */


/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

/*ARGSUSED*/
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yydestruct (const char *yymsg, int yytype, YYSTYPE *yyvaluep, yyscan_t yyscanner)
#else
static void
yydestruct (yymsg, yytype, yyvaluep, yyscanner)
    const char *yymsg;
    int yytype;
    YYSTYPE *yyvaluep;
    yyscan_t yyscanner;
#endif
{
  YYUSE (yyvaluep);
  YYUSE (yyscanner);

  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yytype, yyvaluep, yylocationp);

  switch (yytype)
    {

      default:
	break;
    }
}


/* Prevent warnings from -Wmissing-prototypes.  */

#ifdef YYPARSE_PARAM
#if defined __STDC__ || defined __cplusplus
int yyparse (void *YYPARSE_PARAM);
#else
int yyparse ();
#endif
#else /* ! YYPARSE_PARAM */
#if defined __STDC__ || defined __cplusplus
int yyparse (yyscan_t yyscanner);
#else
int yyparse ();
#endif
#endif /* ! YYPARSE_PARAM */






/*----------.
| yyparse.  |
`----------*/

#ifdef YYPARSE_PARAM
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
int
yyparse (void *YYPARSE_PARAM)
#else
int
yyparse (YYPARSE_PARAM)
    void *YYPARSE_PARAM;
#endif
#else /* ! YYPARSE_PARAM */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
int
yyparse (yyscan_t yyscanner)
#else
int
yyparse (yyscanner)
    yyscan_t yyscanner;
#endif
#endif
{
  /* The look-ahead symbol.  */
int yychar;

/* The semantic value of the look-ahead symbol.  */
YYSTYPE yylval;

/* Number of syntax errors so far.  */
int yynerrs;

  int yystate;
  int yyn;
  int yyresult;
  /* Number of tokens to shift before error messages enabled.  */
  int yyerrstatus;
  /* Look-ahead token as an internal (translated) token number.  */
  int yytoken = 0;
#if YYERROR_VERBOSE
  /* Buffer for error messages, and its allocated size.  */
  char yymsgbuf[128];
  char *yymsg = yymsgbuf;
  YYSIZE_T yymsg_alloc = sizeof yymsgbuf;
#endif

  /* Three stacks and their tools:
     `yyss': related to states,
     `yyvs': related to semantic values,
     `yyls': related to locations.

     Refer to the stacks thru separate pointers, to allow yyoverflow
     to reallocate them elsewhere.  */

  /* The state stack.  */
  yytype_int16 yyssa[YYINITDEPTH];
  yytype_int16 *yyss = yyssa;
  yytype_int16 *yyssp;

  /* The semantic value stack.  */
  YYSTYPE yyvsa[YYINITDEPTH];
  YYSTYPE *yyvs = yyvsa;
  YYSTYPE *yyvsp;



#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N))

  YYSIZE_T yystacksize = YYINITDEPTH;

  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;


  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY;		/* Cause a token to be read.  */

  /* Initialize stack pointers.
     Waste one element of value and location stack
     so that they stay on the same level as the state stack.
     The wasted elements are never initialized.  */

  yyssp = yyss;
  yyvsp = yyvs;

  goto yysetstate;

/*------------------------------------------------------------.
| yynewstate -- Push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
 yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;

 yysetstate:
  *yyssp = yystate;

  if (yyss + yystacksize - 1 <= yyssp)
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYSIZE_T yysize = yyssp - yyss + 1;

#ifdef yyoverflow
      {
	/* Give user a chance to reallocate the stack.  Use copies of
	   these so that the &'s don't force the real ones into
	   memory.  */
	YYSTYPE *yyvs1 = yyvs;
	yytype_int16 *yyss1 = yyss;


	/* Each stack pointer address is followed by the size of the
	   data in use in that stack, in bytes.  This used to be a
	   conditional around just the two extra args, but that might
	   be undefined if yyoverflow is a macro.  */
	yyoverflow (YY_("memory exhausted"),
		    &yyss1, yysize * sizeof (*yyssp),
		    &yyvs1, yysize * sizeof (*yyvsp),

		    &yystacksize);

	yyss = yyss1;
	yyvs = yyvs1;
      }
#else /* no yyoverflow */
# ifndef YYSTACK_RELOCATE
      goto yyexhaustedlab;
# else
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
	goto yyexhaustedlab;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
	yystacksize = YYMAXDEPTH;

      {
	yytype_int16 *yyss1 = yyss;
	union yyalloc *yyptr =
	  (union yyalloc *) YYSTACK_ALLOC (YYSTACK_BYTES (yystacksize));
	if (! yyptr)
	  goto yyexhaustedlab;
	YYSTACK_RELOCATE (yyss);
	YYSTACK_RELOCATE (yyvs);

#  undef YYSTACK_RELOCATE
	if (yyss1 != yyssa)
	  YYSTACK_FREE (yyss1);
      }
# endif
#endif /* no yyoverflow */

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;


      YYDPRINTF ((stderr, "Stack size increased to %lu\n",
		  (unsigned long int) yystacksize));

      if (yyss + yystacksize - 1 <= yyssp)
	YYABORT;
    }

  YYDPRINTF ((stderr, "Entering state %d\n", yystate));

  goto yybackup;

/*-----------.
| yybackup.  |
`-----------*/
yybackup:

  /* Do appropriate processing given the current state.  Read a
     look-ahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to look-ahead token.  */
  yyn = yypact[yystate];
  if (yyn == YYPACT_NINF)
    goto yydefault;

  /* Not known => get a look-ahead token if don't already have one.  */

  /* YYCHAR is either YYEMPTY or YYEOF or a valid look-ahead symbol.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token: "));
      yychar = YYLEX;
    }

  if (yychar <= YYEOF)
    {
      yychar = yytoken = YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yyn == 0 || yyn == YYTABLE_NINF)
	goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  if (yyn == YYFINAL)
    YYACCEPT;

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the look-ahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);

  /* Discard the shifted token unless it is eof.  */
  if (yychar != YYEOF)
    yychar = YYEMPTY;

  yystate = yyn;
  *++yyvsp = yylval;

  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- Do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     `$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];


  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
        case 2:
#line 127 "InputParser/InputParser.yy"
    { solution.start(); int tmp= envt->doallpairs() ; solution.stop(); return tmp; }
    break;

  case 3:
#line 130 "InputParser/InputParser.yy"
    {}
    break;

  case 4:
#line 131 "InputParser/InputParser.yy"
    {}
    break;

  case 5:
#line 132 "InputParser/InputParser.yy"
    {}
    break;

  case 6:
#line 133 "InputParser/InputParser.yy"
    {}
    break;

  case 7:
#line 136 "InputParser/InputParser.yy"
    { 
    if(Gvartype == TUPLE){
        currentBD->create_inputs( -1, OutType::getTuple(tupleName), *(yyvsp[(1) - (1)].strConst));
    }
    else
    if( Gvartype == INT){
		currentBD->create_inputs( 2 /*NINPUTS*/, OutType::INT , *(yyvsp[(1) - (1)].strConst)); 
	}else{
		if(Gvartype==FLOAT){
			currentBD->create_inputs(-1, OutType::FLOAT, *(yyvsp[(1) - (1)].strConst)); 
		}else{
			currentBD->create_inputs(-1, OutType::BOOL, *(yyvsp[(1) - (1)].strConst)); 
		}
	}	

}
    break;

  case 8:
#line 152 "InputParser/InputParser.yy"
    {
	if(Gvartype == TUPLE){
        currentBD->create_inputs( -1, OutType::getTuple(tupleName), *(yyvsp[(1) - (1)].strConst));
    }
    else
    if( Gvartype == INT){
		currentBD->create_inputs( 2 /*NINPUTS*/, OutType::INT , *(yyvsp[(1) - (1)].strConst)); 
	}else{
		if(Gvartype==FLOAT){
			currentBD->create_inputs(-1, OutType::FLOAT, *(yyvsp[(1) - (1)].strConst)); 
		}else{
			currentBD->create_inputs(-1, OutType::BOOL, *(yyvsp[(1) - (1)].strConst)); 
		}
	}	
}
    break;

  case 10:
#line 168 "InputParser/InputParser.yy"
    { 	 currentBD->create_outputs(-1, *(yyvsp[(1) - (1)].strConst)); }
    break;

  case 11:
#line 169 "InputParser/InputParser.yy"
    {
	
	currentBD->create_outputs(-1, *(yyvsp[(1) - (2)].strConst));
}
    break;

  case 12:
#line 175 "InputParser/InputParser.yy"
    {  
	if( (yyvsp[(1) - (2)].variableType) == INT){

		currentBD->create_inputs( 2 /*NINPUTS*/, OutType::INT , *(yyvsp[(2) - (2)].strConst)); 
	}else{
		if((yyvsp[(1) - (2)].variableType) == FLOAT){
			currentBD->create_inputs(-1, OutType::FLOAT, *(yyvsp[(2) - (2)].strConst)); 
		}else{
			currentBD->create_inputs(-1, OutType::BOOL, *(yyvsp[(2) - (2)].strConst)); 
		}
	}	
	delete (yyvsp[(2) - (2)].strConst);
}
    break;

  case 13:
#line 188 "InputParser/InputParser.yy"
    {

    currentBD->create_inputs( -1 , OutType::getTuple(*(yyvsp[(1) - (2)].strConst)), *(yyvsp[(2) - (2)].strConst));
       

    delete (yyvsp[(2) - (2)].strConst);
}
    break;

  case 14:
#line 195 "InputParser/InputParser.yy"
    {

    currentBD->create_inputs( -1 , OutType::getTuple(*(yyvsp[(1) - (5)].strConst)) , *(yyvsp[(2) - (5)].strConst), -1, (yyvsp[(4) - (5)].intConst));
       

    delete (yyvsp[(2) - (5)].strConst);
}
    break;

  case 15:
#line 202 "InputParser/InputParser.yy"
    {
 	 if( (yyvsp[(2) - (3)].variableType) == INT){

		 currentBD->create_outputs(2 /* NINPUTS */, *(yyvsp[(3) - (3)].strConst));
 	 }else{

	 	 currentBD->create_outputs(-1, *(yyvsp[(3) - (3)].strConst)); 
 	 }
	 delete (yyvsp[(3) - (3)].strConst);
 }
    break;

  case 16:
#line 212 "InputParser/InputParser.yy"
    {

    currentBD->create_outputs(-1, *(yyvsp[(3) - (3)].strConst));
    delete (yyvsp[(3) - (3)].strConst);
 }
    break;

  case 17:
#line 218 "InputParser/InputParser.yy"
    {  
	if( (yyvsp[(1) - (6)].variableType) == INT){

		currentBD->create_inputs( 2 /*NINPUTS*/, OutType::INT_ARR , *(yyvsp[(6) - (6)].strConst), (yyvsp[(4) - (6)].intConst)); 
	}else{
		if((yyvsp[(1) - (6)].variableType) == FLOAT){
			currentBD->create_inputs(-1, OutType::FLOAT_ARR, *(yyvsp[(6) - (6)].strConst), (yyvsp[(4) - (6)].intConst)); 
		}else{
			currentBD->create_inputs(-1, OutType::BOOL_ARR, *(yyvsp[(6) - (6)].strConst), (yyvsp[(4) - (6)].intConst)); 
		}
	}	
	delete (yyvsp[(6) - (6)].strConst);
}
    break;

  case 18:
#line 232 "InputParser/InputParser.yy"
    {
    currentBD->create_inputs(-1, OutType::getTuple(*(yyvsp[(1) - (6)].strConst)), *(yyvsp[(6) - (6)].strConst), (yyvsp[(4) - (6)].intConst));
}
    break;

  case 19:
#line 236 "InputParser/InputParser.yy"
    {
 	 if( (yyvsp[(2) - (7)].variableType) == INT){
		 currentBD->create_outputs(2 /* NINPUTS */, *(yyvsp[(7) - (7)].strConst));
 	 }else{

	 	 currentBD->create_outputs(-1, *(yyvsp[(7) - (7)].strConst)); 
 	 }
	 delete (yyvsp[(7) - (7)].strConst);
 }
    break;

  case 20:
#line 245 "InputParser/InputParser.yy"
    {
  currentBD->create_outputs(-1,*(yyvsp[(7) - (7)].strConst));
 }
    break;

  case 21:
#line 248 "InputParser/InputParser.yy"
    {Gvartype = (yyvsp[(1) - (4)].variableType); }
    break;

  case 23:
#line 249 "InputParser/InputParser.yy"
    {Gvartype = TUPLE; tupleName = *(yyvsp[(1) - (4)].strConst);}
    break;

  case 30:
#line 258 "InputParser/InputParser.yy"
    {isModel=true; }
    break;

  case 31:
#line 258 "InputParser/InputParser.yy"
    {isModel=false; }
    break;

  case 32:
#line 261 "InputParser/InputParser.yy"
    {		modelBuilding.restart ();
		if(currentBD!= NULL){
			delete currentBD;
		}

		currentBD = envt->newFunction(*(yyvsp[(2) - (2)].strConst), isModel);

		delete (yyvsp[(2) - (2)].strConst);

}
    break;

  case 33:
#line 271 "InputParser/InputParser.yy"
    { 
	currentBD->finalize();
	modelBuilding.stop();
}
    break;

  case 34:
#line 277 "InputParser/InputParser.yy"
    {
    if( (yyvsp[(1) - (1)].variableType) == INT){ (yyval.otype) = OutType::INT;}
    if( (yyvsp[(1) - (1)].variableType) == BIT){ (yyval.otype) = OutType::BOOL;}
    if( (yyvsp[(1) - (1)].variableType) == INT_ARR){ (yyval.otype) = OutType::INT_ARR;}
    if( (yyvsp[(1) - (1)].variableType) == BIT_ARR){ (yyval.otype) = OutType::BOOL_ARR;}
    if( (yyvsp[(1) - (1)].variableType) == FLOAT_ARR){ (yyval.otype) = OutType::FLOAT_ARR;}
}
    break;

  case 35:
#line 284 "InputParser/InputParser.yy"
    {
    if ((yyvsp[(1) - (5)].variableType) == INT) {(yyval.otype) = OutType::INT_ARR;}
    if( (yyvsp[(1) - (5)].variableType) == BIT){ (yyval.otype) = OutType::BOOL_ARR;}
    if( (yyvsp[(1) - (5)].variableType) == FLOAT){ (yyval.otype) = OutType::FLOAT_ARR;}
}
    break;

  case 36:
#line 289 "InputParser/InputParser.yy"
    {
  (yyval.otype) = ((Tuple*)OutType::getTuple(*(yyvsp[(1) - (5)].strConst)))->arr;
}
    break;

  case 37:
#line 292 "InputParser/InputParser.yy"
    { 
    (yyval.otype) = OutType::getTuple(*(yyvsp[(1) - (1)].strConst));
}
    break;

  case 38:
#line 296 "InputParser/InputParser.yy"
    {/* Empty */  (yyval.tVector) = new vector<OutType*>(); }
    break;

  case 39:
#line 297 "InputParser/InputParser.yy"
    {
    (yyvsp[(1) - (2)].tVector)->push_back( (yyvsp[(2) - (2)].otype) );
    (yyval.tVector) = (yyvsp[(1) - (2)].tVector);
}
    break;

  case 40:
#line 301 "InputParser/InputParser.yy"
    {
    OutType* type;
    if ((yyvsp[(2) - (5)].variableType) == INT) {type = OutType::INT_ARR;}
    if( (yyvsp[(2) - (5)].variableType) == BIT){ type = OutType::BOOL_ARR;}
    if( (yyvsp[(2) - (5)].variableType) == FLOAT){type = OutType::FLOAT_ARR;}
    for (int i = 0; i < (yyvsp[(4) - (5)].intConst); i++ ) {
        (yyvsp[(1) - (5)].tVector)->push_back (type );
    }
    (yyval.tVector) = (yyvsp[(1) - (5)].tVector);

}
    break;

  case 41:
#line 313 "InputParser/InputParser.yy"
    {
//add type
    OutType::makeTuple(*(yyvsp[(1) - (4)].strConst), *(yyvsp[(3) - (4)].tVector), -1);

}
    break;

  case 42:
#line 318 "InputParser/InputParser.yy"
    {
    OutType::makeTuple(*(yyvsp[(1) - (5)].strConst), *(yyvsp[(4) - (5)].tVector), (yyvsp[(3) - (5)].intConst));
}
    break;

  case 43:
#line 322 "InputParser/InputParser.yy"
    { /* Empty */ }
    break;

  case 44:
#line 323 "InputParser/InputParser.yy"
    { }
    break;

  case 45:
#line 325 "InputParser/InputParser.yy"
    {/* Empty */}
    break;

  case 46:
#line 326 "InputParser/InputParser.yy"
    { }
    break;

  case 47:
#line 328 "InputParser/InputParser.yy"
    {
  envt->registerFunctionReplace(*(yyvsp[(4) - (10)].strConst), *(yyvsp[(2) - (10)].strConst), *(yyvsp[(6) - (10)].strConst), (yyvsp[(8) - (10)].intConst));
}
    break;

  case 48:
#line 334 "InputParser/InputParser.yy"
    {
	if(PARAMS->interactive){
		(yyval.bdag) = envt->prepareMiter(envt->getCopy(*(yyvsp[(3) - (3)].strConst)),  envt->getCopy(*(yyvsp[(1) - (3)].strConst)));
	}else{
		envt->addspskpair(*(yyvsp[(3) - (3)].strConst), *(yyvsp[(1) - (3)].strConst));
	}		
}
    break;

  case 49:
#line 342 "InputParser/InputParser.yy"
    {if(PARAMS->interactive){ solution.restart();} }
    break;

  case 50:
#line 343 "InputParser/InputParser.yy"
    {
	if(PARAMS->interactive){
		int tt = envt->assertDAG((yyvsp[(3) - (4)].bdag), std::cout);
		envt->printControls("");
		solution.stop();
		cout<<"COMPLETED"<<endl;
		if(tt != 0){
			return tt;
		}
	}
}
    break;

  case 51:
#line 355 "InputParser/InputParser.yy"
    {
	int tt = envt->runCommand(*(yyvsp[(1) - (5)].strConst), *(yyvsp[(3) - (5)].sList));
	delete (yyvsp[(1) - (5)].strConst);
	delete (yyvsp[(3) - (5)].sList);
	if(tt >= 0){
		return tt;
	}
}
    break;

  case 52:
#line 364 "InputParser/InputParser.yy"
    {
	(yyval.sList) = new list<string*>();	
}
    break;

  case 53:
#line 367 "InputParser/InputParser.yy"
    {
	(yyval.sList) = (yyvsp[(2) - (2)].sList);
	(yyval.sList)->push_back( (yyvsp[(1) - (2)].strConst));
}
    break;

  case 54:
#line 371 "InputParser/InputParser.yy"
    {
	(yyval.sList) = (yyvsp[(2) - (2)].sList);
	(yyval.sList)->push_back( (yyvsp[(1) - (2)].strConst));
}
    break;

  case 55:
#line 377 "InputParser/InputParser.yy"
    { /* Empty */ }
    break;

  case 56:
#line 378 "InputParser/InputParser.yy"
    { /* */ }
    break;

  case 57:
#line 381 "InputParser/InputParser.yy"
    {  (yyval.intConst)=0;  /* */ }
    break;

  case 58:
#line 382 "InputParser/InputParser.yy"
    {
	currentBD->alias( *(yyvsp[(1) - (4)].strConst), (yyvsp[(3) - (4)].bnode));
	delete (yyvsp[(1) - (4)].strConst);
}
    break;

  case 59:
#line 386 "InputParser/InputParser.yy"
    {

	list<string*>* childs = (yyvsp[(2) - (11)].sList);
	list<string*>::reverse_iterator it = childs->rbegin();
	
	list<bool_node*>* oldchilds = (yyvsp[(4) - (11)].nList);
	list<bool_node*>::reverse_iterator oldit = oldchilds->rbegin();
	
	bool_node* rhs;
	rhs = (yyvsp[(10) - (11)].bnode);
	int bigN = childs->size();
	Assert( bigN == oldchilds->size(), "This can't happen");	

	for(int i=0; i<bigN; ++i, ++it, ++oldit){		
		ARRASS_node* an = dynamic_cast<ARRASS_node*>(newNode(bool_node::ARRASS));
		an->multi_mother.reserve(2);
		an->multi_mother.push_back(*oldit);			
		an->multi_mother.push_back(rhs);
		Assert( rhs != NULL, "AAARRRGH This shouldn't happen !!");
		Assert((yyvsp[(7) - (11)].bnode) != NULL, "1: THIS CAN'T HAPPEN!!");
		an->quant = i;		
		currentBD->alias( *(*it), currentBD->new_node((yyvsp[(7) - (11)].bnode),  NULL,  an) );
		delete *it;
	}
	delete childs;
	delete oldchilds;	
}
    break;

  case 60:
#line 414 "InputParser/InputParser.yy"
    {
	Assert(false, "UNREACHABLE");
	currentBD->create_outputs(2 /*NINPUTS*/, (yyvsp[(3) - (4)].bnode), *(yyvsp[(1) - (4)].strConst));
	delete (yyvsp[(1) - (4)].strConst);
}
    break;

  case 61:
#line 419 "InputParser/InputParser.yy"
    {
  if ((yyvsp[(2) - (3)].bnode)) {
    /* Asserting an expression, construct assert node. */
    
    currentBD->new_node ((yyvsp[(2) - (3)].bnode), NULL, bool_node::ASSERT);
  }
}
    break;

  case 62:
#line 426 "InputParser/InputParser.yy"
    {
  if ((yyvsp[(2) - (5)].bnode)) {
    /* Asserting an expression, construct assert node. */
	if(!((yyvsp[(2) - (5)].bnode)->type == bool_node::CONST && dynamic_cast<CONST_node*>((yyvsp[(2) - (5)].bnode))->getVal() == 1)){
		ASSERT_node* bn = dynamic_cast<ASSERT_node*>(newNode(bool_node::ASSERT));
		bn->setMsg(*(yyvsp[(4) - (5)].strConst));
		currentBD->new_node ((yyvsp[(2) - (5)].bnode), NULL, bn);
	}    
    delete (yyvsp[(4) - (5)].strConst);
  }
}
    break;

  case 63:
#line 437 "InputParser/InputParser.yy"
    {
  if ((yyvsp[(2) - (3)].bnode)) {
    /* Asserting an expression, construct assert node. */
    
    ASSERT_node* bn = dynamic_cast<ASSERT_node*>(newNode(bool_node::ASSERT));
    bn->makeHardAssert();
    currentBD->new_node((yyvsp[(2) - (3)].bnode), NULL, bn);
  }
}
    break;

  case 64:
#line 446 "InputParser/InputParser.yy"
    {
  if ((yyvsp[(2) - (5)].bnode)) {
    /* Asserting an expression, construct assert node. */
	if(!((yyvsp[(2) - (5)].bnode)->type == bool_node::CONST && dynamic_cast<CONST_node*>((yyvsp[(2) - (5)].bnode))->getVal() == 1)){
		ASSERT_node* bn = dynamic_cast<ASSERT_node*>(newNode(bool_node::ASSERT));
		bn->setMsg(*(yyvsp[(4) - (5)].strConst));
    bn->makeHardAssert();
		currentBD->new_node ((yyvsp[(2) - (5)].bnode), NULL, bn);
	}    
    delete (yyvsp[(4) - (5)].strConst);
  }
}
    break;

  case 65:
#line 459 "InputParser/InputParser.yy"
    {
  if ((yyvsp[(2) - (4)].bnode)) {
    /* Asserting an expression, construct assert node. */
	if(!((yyvsp[(2) - (4)].bnode)->type == bool_node::CONST && dynamic_cast<CONST_node*>((yyvsp[(2) - (4)].bnode))->getVal() == 1)){
		ASSERT_node* bn = dynamic_cast<ASSERT_node*>(newNode(bool_node::ASSERT));
		bn->makeAssume();
		if ((yyvsp[(3) - (4)].strConst)) {
			bn->setMsg(*(yyvsp[(3) - (4)].strConst));
		}
		currentBD->new_node ((yyvsp[(2) - (4)].bnode), NULL, bn);
	}
	if ((yyvsp[(3) - (4)].strConst)) {
		delete (yyvsp[(3) - (4)].strConst);
	}
  }
}
    break;

  case 66:
#line 476 "InputParser/InputParser.yy"
    { (yyval.strConst) = (yyvsp[(2) - (2)].strConst); }
    break;

  case 67:
#line 477 "InputParser/InputParser.yy"
    { (yyval.strConst) = 0; }
    break;

  case 68:
#line 479 "InputParser/InputParser.yy"
    { (yyval.bnode) = (yyvsp[(1) - (1)].bnode); }
    break;

  case 69:
#line 480 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::AND);	
}
    break;

  case 70:
#line 483 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::AND);
}
    break;

  case 71:
#line 486 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::OR);	
}
    break;

  case 72:
#line 489 "InputParser/InputParser.yy"
    { 	
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::OR);	
}
    break;

  case 73:
#line 492 "InputParser/InputParser.yy"
    {	
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::XOR);	
}
    break;

  case 74:
#line 495 "InputParser/InputParser.yy"
    {	
	bool_node* tmp = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::EQ);
	(yyval.bnode) = currentBD->new_node (tmp, NULL, bool_node::NOT);	
}
    break;

  case 75:
#line 499 "InputParser/InputParser.yy"
    { 			
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::EQ);
}
    break;

  case 76:
#line 502 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->new_node((yyvsp[(3) - (4)].bnode), (yyvsp[(1) - (4)].bnode), bool_node::ARR_R);
}
    break;

  case 77:
#line 505 "InputParser/InputParser.yy"
    {
   
	//TUPLE_R_node* tn = dynamic_cast<TUPLE_R_node*>();
    
	//tn->idx = $4;
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (5)].bnode), (yyvsp[(4) - (5)].intConst));
	
}
    break;

  case 78:
#line 513 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->new_node((yyvsp[(3) - (4)].bnode), currentBD->create_const((yyvsp[(1) - (4)].intConst)), bool_node::ARR_R);		
}
    break;

  case 79:
#line 516 "InputParser/InputParser.yy"
    {
	ARR_W_node* an = dynamic_cast<ARR_W_node*>(newNode(bool_node::ARR_W));
	an->multi_mother.push_back( currentBD->get_node(*(yyvsp[(1) - (8)].strConst)) );
	an->multi_mother.push_back( (yyvsp[(6) - (8)].bnode) );
	(yyval.bnode) = currentBD->new_node((yyvsp[(4) - (8)].bnode), NULL, an);	
	delete (yyvsp[(1) - (8)].strConst);
}
    break;

  case 80:
#line 523 "InputParser/InputParser.yy"
    {
	ARR_W_node* an = dynamic_cast<ARR_W_node*>(newNode(bool_node::ARR_W));
	an->multi_mother.push_back( currentBD->create_const((yyvsp[(1) - (8)].intConst)) );
	an->multi_mother.push_back( (yyvsp[(6) - (8)].bnode) );
	(yyval.bnode) = currentBD->new_node((yyvsp[(4) - (8)].bnode), NULL, an);		
}
    break;

  case 81:
#line 529 "InputParser/InputParser.yy"
    {
	int pushval = 0;
	arith_node* an = dynamic_cast<arith_node*>(newNode(bool_node::ARRACC));
	list<bool_node*>* childs = (yyvsp[(2) - (6)].nList);
	list<bool_node*>::reverse_iterator it = childs->rbegin();
	int bigN = childs->size();
	an->multi_mother.reserve(bigN);
	for(int i=0; i<bigN; ++i, ++it){
		an->multi_mother.push_back(*it);
	}		
	Assert((yyvsp[(5) - (6)].bnode) != NULL, "2: THIS CAN'T HAPPEN!!");	
	(yyval.bnode) = currentBD->new_node((yyvsp[(5) - (6)].bnode), NULL,  an);
	delete childs;	
}
    break;

  case 82:
#line 543 "InputParser/InputParser.yy"
    {
	arith_node* an = dynamic_cast<arith_node*>(newNode(bool_node::ARR_CREATE));
    

	list<bool_node*>* childs = (yyvsp[(2) - (3)].nList);
	list<bool_node*>::reverse_iterator it = childs->rbegin();
	int bigN = childs->size();
	an->multi_mother.reserve(bigN);
	for(int i=0; i<bigN; ++i, ++it){
		an->multi_mother.push_back(*it);
	}		
	(yyval.bnode) = currentBD->new_node(NULL, NULL, an); 
	delete childs;
}
    break;

  case 83:
#line 557 "InputParser/InputParser.yy"
    {

	arith_node* an = dynamic_cast<arith_node*>(newNode(bool_node::TUPLE_CREATE));

	list<bool_node*>* childs = (yyvsp[(5) - (6)].nList);
	list<bool_node*>::reverse_iterator it = childs->rbegin();
	int bigN = childs->size();
	an->multi_mother.reserve(bigN);
	for(int i=0; i<bigN; ++i, ++it){
		an->multi_mother.push_back(*it);
	}
    (dynamic_cast<TUPLE_CREATE_node*>(an))->setName(*(yyvsp[(2) - (6)].strConst));
	(yyval.bnode) = currentBD->new_node(NULL, NULL, an); 
	delete childs;
}
    break;

  case 84:
#line 572 "InputParser/InputParser.yy"
    {
	arith_node* an = dynamic_cast<arith_node*>(newNode(bool_node::ACTRL));
	list<bool_node*>* childs = (yyvsp[(2) - (3)].nList);
	list<bool_node*>::reverse_iterator it = childs->rbegin();
	int bigN = childs->size();
	an->multi_mother.reserve(bigN);
	for(int i=0; i<bigN; ++i, ++it){
		an->multi_mother.push_back(*it);
	}		
	(yyval.bnode) = currentBD->new_node(NULL, NULL, an); 
	delete childs;
}
    break;

  case 85:
#line 585 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::PLUS); 	
}
    break;

  case 86:
#line 589 "InputParser/InputParser.yy"
    {	
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::DIV); 	
}
    break;

  case 87:
#line 593 "InputParser/InputParser.yy"
    {	
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::MOD); 	
}
    break;

  case 88:
#line 597 "InputParser/InputParser.yy"
    {
	(yyval.bnode)= currentBD->new_node((yyvsp[(1) - (3)].bnode),  (yyvsp[(3) - (3)].bnode), bool_node::TIMES);
}
    break;

  case 89:
#line 600 "InputParser/InputParser.yy"
    {
	bool_node* neg1 = currentBD->new_node((yyvsp[(3) - (3)].bnode), NULL, bool_node::NEG);
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode), neg1, bool_node::PLUS); 	
}
    break;

  case 90:
#line 604 "InputParser/InputParser.yy"
    {
	
	(yyval.bnode) = currentBD->new_node((yyvsp[(3) - (3)].bnode), (yyvsp[(1) - (3)].bnode), bool_node::LT);     
}
    break;

  case 91:
#line 608 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (3)].bnode), (yyvsp[(3) - (3)].bnode), bool_node::LT);
}
    break;

  case 92:
#line 611 "InputParser/InputParser.yy"
    {
	bool_node* tmp = currentBD->new_node((yyvsp[(1) - (3)].bnode), (yyvsp[(3) - (3)].bnode), bool_node::LT);
	(yyval.bnode) = currentBD->new_node(tmp, NULL, bool_node::NOT);
}
    break;

  case 93:
#line 615 "InputParser/InputParser.yy"
    {
	bool_node* tmp = currentBD->new_node((yyvsp[(3) - (3)].bnode), (yyvsp[(1) - (3)].bnode), bool_node::LT);
	(yyval.bnode) = currentBD->new_node(tmp, NULL, bool_node::NOT);
}
    break;

  case 94:
#line 619 "InputParser/InputParser.yy"
    {
	arith_node* an = dynamic_cast<arith_node*>(newNode(bool_node::ARRACC));
	bool_node* yesChild =((yyvsp[(3) - (5)].bnode));
	bool_node* noChild = ((yyvsp[(5) - (5)].bnode));
	an->multi_mother.push_back( noChild );
	an->multi_mother.push_back( yesChild );	
	(yyval.bnode) = currentBD->new_node((yyvsp[(1) - (5)].bnode), NULL, an); 	
}
    break;

  case 95:
#line 630 "InputParser/InputParser.yy"
    { /* Empty */  	(yyval.nList) = new list<bool_node*>();	}
    break;

  case 96:
#line 631 "InputParser/InputParser.yy"
    {

//The signs are already in the stack by default. All I have to do is not remove them.
	if((yyvsp[(1) - (2)].bnode) != NULL){
		(yyvsp[(2) - (2)].nList)->push_back( (yyvsp[(1) - (2)].bnode) );
	}else{
		(yyvsp[(2) - (2)].nList)->push_back( NULL );
	}
	(yyval.nList) = (yyvsp[(2) - (2)].nList);
}
    break;

  case 97:
#line 642 "InputParser/InputParser.yy"
    {
	(yyval.sList) = new list<string*>();	
	(yyval.sList)->push_back( (yyvsp[(1) - (1)].strConst));
}
    break;

  case 98:
#line 646 "InputParser/InputParser.yy"
    {
	(yyval.sList) = (yyvsp[(2) - (2)].sList);
	(yyval.sList)->push_back( (yyvsp[(1) - (2)].strConst));
}
    break;

  case 99:
#line 651 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->create_const((yyvsp[(1) - (1)].intConst));
}
    break;

  case 100:
#line 654 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->create_const((yyvsp[(1) - (1)].doubleConst));
}
    break;

  case 101:
#line 658 "InputParser/InputParser.yy"
    {
    
	list<bool_node*>* params = (yyvsp[(7) - (16)].nList);
	if(false && params->size() == 0){

        (yyval.bnode) = currentBD->create_inputs(-1,OutType::getTuple(*(yyvsp[(4) - (16)].strConst)), *(yyvsp[(1) - (16)].strConst));

		delete (yyvsp[(1) - (16)].strConst);
	}else{	
		string& fname = *(yyvsp[(1) - (16)].strConst);
		list<bool_node*>::reverse_iterator parit = params->rbegin();
		UFUN_node* ufun = new UFUN_node(fname);
		ufun->outname = *(yyvsp[(13) - (16)].strConst);
		int fgid = (yyvsp[(15) - (16)].intConst);
		ufun->fgid = fgid;
		bool_node* pCond;	
		for( ; parit != params->rend(); ++parit){
            ufun->multi_mother.push_back((*parit));
        }
        pCond = (yyvsp[(10) - (16)].bnode);


        ufun->set_nbits( 0 );
        ufun->set_tupleName(*(yyvsp[(4) - (16)].strConst));
		
		
		//ufun->name = (currentBD->new_name(fname));
		(yyval.bnode) = currentBD->new_node(pCond, NULL, ufun);

        delete (yyvsp[(1) - (16)].strConst);
		delete (yyvsp[(13) - (16)].strConst);
	}
	delete (yyvsp[(7) - (16)].nList);

}
    break;

  case 102:
#line 693 "InputParser/InputParser.yy"
    {
	
	list<bool_node*>* params = (yyvsp[(6) - (15)].nList);
	if(false && params->size() == 0){
		if( (yyvsp[(3) - (15)].variableType) == INT){
			(yyval.bnode) = currentBD->create_inputs( 2 /*NINPUTS*/, OutType::INT , *(yyvsp[(1) - (15)].strConst)); 
		}else{
			if((yyvsp[(3) - (15)].variableType)==FLOAT){
				(yyval.bnode) = currentBD->create_inputs(-1,OutType::FLOAT, *(yyvsp[(1) - (15)].strConst));
			}else{
				(yyval.bnode) = currentBD->create_inputs(-1,OutType::BOOL, *(yyvsp[(1) - (15)].strConst));
			}
		}
		delete (yyvsp[(1) - (15)].strConst);
	}else{	
		string& fname = *(yyvsp[(1) - (15)].strConst);
		list<bool_node*>::reverse_iterator parit = params->rbegin();
		UFUN_node* ufun = new UFUN_node(fname);
		ufun->outname = *(yyvsp[(12) - (15)].strConst);
		int fgid = (yyvsp[(14) - (15)].intConst);
		ufun->fgid = fgid;	
		bool_node* pCond;	

        for( ; parit != params->rend(); ++parit){
            ufun->multi_mother.push_back((*parit));
        }
        pCond = (yyvsp[(9) - (15)].bnode);

		
		if( (yyvsp[(3) - (15)].variableType) == INT || (yyvsp[(3) - (15)].variableType)==INT_ARR){
			ufun->set_nbits( 2 /*NINPUTS*/  );
		}else{	
			ufun->set_nbits( 1  );
		}
		if((yyvsp[(3) - (15)].variableType) == INT_ARR || (yyvsp[(3) - (15)].variableType)==BIT_ARR){
			ufun->makeArr();
		}
		
		//ufun->name = (currentBD->new_name(fname));
		(yyval.bnode) = currentBD->new_node(pCond, NULL, ufun);

		
		delete (yyvsp[(1) - (15)].strConst);
		delete (yyvsp[(12) - (15)].strConst);
	}
	delete (yyvsp[(6) - (15)].nList);
}
    break;

  case 103:
#line 741 "InputParser/InputParser.yy"
    {		
		(yyval.bnode) = currentBD->new_node((yyvsp[(2) - (2)].bnode), NULL, bool_node::NEG);				
}
    break;

  case 104:
#line 744 "InputParser/InputParser.yy"
    { 
	(yyval.bnode) = currentBD->new_node((yyvsp[(2) - (2)].bnode), NULL, bool_node::NOT);		    
}
    break;

  case 105:
#line 748 "InputParser/InputParser.yy"
    { 
						(yyval.bnode) = (yyvsp[(2) - (3)].bnode); 
						}
    break;

  case 106:
#line 751 "InputParser/InputParser.yy"
    { 			
			(yyval.bnode) = currentBD->get_node(*(yyvsp[(1) - (1)].strConst));
			delete (yyvsp[(1) - (1)].strConst);				
			 
		}
    break;

  case 107:
#line 756 "InputParser/InputParser.yy"
    {		
	(yyval.bnode) = currentBD->create_controls(-1, *(yyvsp[(2) - (3)].strConst));
	delete (yyvsp[(2) - (3)].strConst);
}
    break;

  case 108:
#line 760 "InputParser/InputParser.yy"
    {
	int nctrls = (yyvsp[(3) - (4)].intConst);
	if(overrideNCtrls){
		nctrls = NCTRLS;
	}
	(yyval.bnode) = currentBD->create_controls(nctrls, *(yyvsp[(2) - (4)].strConst));
	delete (yyvsp[(2) - (4)].strConst);
}
    break;

  case 109:
#line 768 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->create_controls((yyvsp[(3) - (5)].intConst), *(yyvsp[(2) - (5)].strConst));
	delete (yyvsp[(2) - (5)].strConst);

}
    break;

  case 110:
#line 773 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->create_controls(-1, *(yyvsp[(2) - (4)].strConst), false, true);
	delete (yyvsp[(2) - (4)].strConst);
}
    break;

  case 111:
#line 777 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->create_controls((yyvsp[(3) - (5)].intConst), *(yyvsp[(2) - (5)].strConst), false, true);
	delete (yyvsp[(2) - (5)].strConst);
}
    break;

  case 112:
#line 781 "InputParser/InputParser.yy"
    {		
	(yyval.bnode) = currentBD->create_controls(-1, *(yyvsp[(3) - (4)].strConst), true);
	delete (yyvsp[(3) - (4)].strConst);
}
    break;

  case 113:
#line 785 "InputParser/InputParser.yy"
    {
	int nctrls = (yyvsp[(4) - (5)].intConst);
	if(overrideNCtrls){
		nctrls = NCTRLS;
	}
	(yyval.bnode) = currentBD->create_controls(nctrls, *(yyvsp[(3) - (5)].strConst), true);
	delete (yyvsp[(3) - (5)].strConst);
}
    break;

  case 114:
#line 793 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->create_controls((yyvsp[(4) - (6)].intConst), *(yyvsp[(3) - (6)].strConst), true);
	delete (yyvsp[(3) - (6)].strConst);

}
    break;

  case 115:
#line 798 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->create_controls(-1, *(yyvsp[(7) - (8)].strConst), false, false, true, (yyvsp[(2) - (8)].intConst));
  ((CTRL_node*) (yyval.bnode))->setParents(*(yyvsp[(4) - (8)].sVector));
	delete (yyvsp[(7) - (8)].strConst);
}
    break;

  case 116:
#line 803 "InputParser/InputParser.yy"
    {
	int nctrls = (yyvsp[(8) - (9)].intConst);
	if(overrideNCtrls){
		nctrls = NCTRLS;
	}
	(yyval.bnode) = currentBD->create_controls(nctrls, *(yyvsp[(7) - (9)].strConst), false, false, true, (yyvsp[(2) - (9)].intConst));
  ((CTRL_node*) (yyval.bnode))->setParents(*(yyvsp[(4) - (9)].sVector));
	delete (yyvsp[(7) - (9)].strConst);
}
    break;

  case 117:
#line 812 "InputParser/InputParser.yy"
    {
	(yyval.bnode) = currentBD->create_controls((yyvsp[(8) - (10)].intConst), *(yyvsp[(7) - (10)].strConst), false, false, true, (yyvsp[(2) - (10)].intConst));
  ((CTRL_node*) (yyval.bnode))->setParents(*(yyvsp[(4) - (10)].sVector));
	delete (yyvsp[(7) - (10)].strConst);

}
    break;

  case 118:
#line 819 "InputParser/InputParser.yy"
    { /* Empty */  	(yyval.sVector) = new vector<string>();	}
    break;

  case 119:
#line 820 "InputParser/InputParser.yy"
    {
  (yyvsp[(1) - (2)].sVector)->push_back(*(yyvsp[(2) - (2)].strConst));
	(yyval.sVector) = (yyvsp[(1) - (2)].sVector);
}
    break;

  case 120:
#line 825 "InputParser/InputParser.yy"
    { (yyval.intConst) = (yyvsp[(1) - (1)].intConst); }
    break;

  case 121:
#line 826 "InputParser/InputParser.yy"
    { (yyval.intConst) = (yyvsp[(1) - (3)].intConst) + (yyvsp[(3) - (3)].intConst); }
    break;

  case 122:
#line 827 "InputParser/InputParser.yy"
    { (yyval.intConst) = (yyvsp[(1) - (3)].intConst) - (yyvsp[(3) - (3)].intConst); }
    break;

  case 123:
#line 829 "InputParser/InputParser.yy"
    { (yyval.intConst) = (yyvsp[(1) - (1)].intConst); }
    break;

  case 124:
#line 830 "InputParser/InputParser.yy"
    { (yyval.intConst) = (yyvsp[(2) - (3)].intConst); }
    break;

  case 125:
#line 831 "InputParser/InputParser.yy"
    { (yyval.intConst) = (yyvsp[(1) - (3)].intConst) * (yyvsp[(3) - (3)].intConst); }
    break;

  case 126:
#line 832 "InputParser/InputParser.yy"
    { Assert( (yyvsp[(3) - (3)].intConst) != 0, "You are attempting to divide by zero !!");
							      (yyval.intConst) = (yyvsp[(1) - (3)].intConst) / (yyvsp[(3) - (3)].intConst); }
    break;

  case 127:
#line 834 "InputParser/InputParser.yy"
    { Assert( (yyvsp[(3) - (3)].intConst) != 0, "You are attempting to mod by zero !!");
							      (yyval.intConst) = (yyvsp[(1) - (3)].intConst) % (yyvsp[(3) - (3)].intConst); }
    break;

  case 128:
#line 838 "InputParser/InputParser.yy"
    {  (yyval.intConst) = (yyvsp[(1) - (1)].intConst); }
    break;

  case 129:
#line 839 "InputParser/InputParser.yy"
    {  (yyval.intConst) = -(yyvsp[(2) - (2)].intConst); }
    break;

  case 130:
#line 842 "InputParser/InputParser.yy"
    {  (yyval.intConst) = (yyvsp[(1) - (1)].intConst); }
    break;

  case 131:
#line 843 "InputParser/InputParser.yy"
    { (yyval.intConst) = 1; }
    break;

  case 132:
#line 844 "InputParser/InputParser.yy"
    { (yyval.intConst) = 0; }
    break;

  case 133:
#line 846 "InputParser/InputParser.yy"
    { (yyval.strConst)=(yyvsp[(1) - (1)].strConst); }
    break;


/* Line 1267 of yacc.c.  */
#line 2920 "InputParser.cpp"
      default: break;
    }
  YY_SYMBOL_PRINT ("-> $$ =", yyr1[yyn], &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);

  *++yyvsp = yyval;


  /* Now `shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */

  yyn = yyr1[yyn];

  yystate = yypgoto[yyn - YYNTOKENS] + *yyssp;
  if (0 <= yystate && yystate <= YYLAST && yycheck[yystate] == *yyssp)
    yystate = yytable[yystate];
  else
    yystate = yydefgoto[yyn - YYNTOKENS];

  goto yynewstate;


/*------------------------------------.
| yyerrlab -- here on detecting error |
`------------------------------------*/
yyerrlab:
  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
#if ! YYERROR_VERBOSE
      yyerror (yyscanner, YY_("syntax error"));
#else
      {
	YYSIZE_T yysize = yysyntax_error (0, yystate, yychar);
	if (yymsg_alloc < yysize && yymsg_alloc < YYSTACK_ALLOC_MAXIMUM)
	  {
	    YYSIZE_T yyalloc = 2 * yysize;
	    if (! (yysize <= yyalloc && yyalloc <= YYSTACK_ALLOC_MAXIMUM))
	      yyalloc = YYSTACK_ALLOC_MAXIMUM;
	    if (yymsg != yymsgbuf)
	      YYSTACK_FREE (yymsg);
	    yymsg = (char *) YYSTACK_ALLOC (yyalloc);
	    if (yymsg)
	      yymsg_alloc = yyalloc;
	    else
	      {
		yymsg = yymsgbuf;
		yymsg_alloc = sizeof yymsgbuf;
	      }
	  }

	if (0 < yysize && yysize <= yymsg_alloc)
	  {
	    (void) yysyntax_error (yymsg, yystate, yychar);
	    yyerror (yyscanner, yymsg);
	  }
	else
	  {
	    yyerror (yyscanner, YY_("syntax error"));
	    if (yysize != 0)
	      goto yyexhaustedlab;
	  }
      }
#endif
    }



  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse look-ahead token after an
	 error, discard it.  */

      if (yychar <= YYEOF)
	{
	  /* Return failure if at end of input.  */
	  if (yychar == YYEOF)
	    YYABORT;
	}
      else
	{
	  yydestruct ("Error: discarding",
		      yytoken, &yylval, yyscanner);
	  yychar = YYEMPTY;
	}
    }

  /* Else will try to reuse look-ahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:

  /* Pacify compilers like GCC when the user code never invokes
     YYERROR and the label yyerrorlab therefore never appears in user
     code.  */
  if (/*CONSTCOND*/ 0)
     goto yyerrorlab;

  /* Do not reclaim the symbols of the rule which action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;	/* Each real token shifted decrements this.  */

  for (;;)
    {
      yyn = yypact[yystate];
      if (yyn != YYPACT_NINF)
	{
	  yyn += YYTERROR;
	  if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYTERROR)
	    {
	      yyn = yytable[yyn];
	      if (0 < yyn)
		break;
	    }
	}

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
	YYABORT;


      yydestruct ("Error: popping",
		  yystos[yystate], yyvsp, yyscanner);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  if (yyn == YYFINAL)
    YYACCEPT;

  *++yyvsp = yylval;


  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", yystos[yyn], yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturn;

/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturn;

#ifndef yyoverflow
/*-------------------------------------------------.
| yyexhaustedlab -- memory exhaustion comes here.  |
`-------------------------------------------------*/
yyexhaustedlab:
  yyerror (yyscanner, YY_("memory exhausted"));
  yyresult = 2;
  /* Fall through.  */
#endif

yyreturn:
  if (yychar != YYEOF && yychar != YYEMPTY)
     yydestruct ("Cleanup: discarding lookahead",
		 yytoken, &yylval, yyscanner);
  /* Do not reclaim the symbols of the rule which action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
		  yystos[*yyssp], yyvsp, yyscanner);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif
#if YYERROR_VERBOSE
  if (yymsg != yymsgbuf)
    YYSTACK_FREE (yymsg);
#endif
  /* Make sure YYID is used.  */
  return YYID (yyresult);
}


#line 848 "InputParser/InputParser.yy"



void Inityyparse(){

	 	
}

void yyerror( void* yyscanner, const char* c){
	Assert(false, (char *)c); 
}


int isatty(int i){



return 1;
}

