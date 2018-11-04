(ns twitter.api-client
    (:require [clojure.edn :as edn]
              [environ.core :refer [env]])
    (:use [repository.entity-manager]
          [twitter.oauth]
          [twitter.callbacks]
          [twitter.callbacks.handlers]
          [twitter.api.restful]))

(def ^:dynamic *skip-api-calls* true)

(defn twitter-credentials
  "Make Twitter OAuth credentials from the environment configuration"
  ; @see https://github.com/adamwynne/twitter-api#restful-calls
  [& args]
  (let [credentials (edn/read-string (:twitter env))]
    (make-oauth-creds (:consumer-key credentials)
                      (:consumer-secret credentials)
                      (:token credentials)
                      (:secret credentials))))

(defn get-member-by-screen-name
  [screen-name]
  (if *skip-api-calls*
    {}
    (users-show :oauth-creds (twitter-credentials)
              :params {:screen-name screen-name})))

(defn get-id-of-member-having-username
  [screen-name members]
  (let [matching-members (find-member-by-screen-name screen-name members)]
    (def member (if matching-members
      (first matching-members)
      (get-member-by-screen-name screen-name)))
    (:id member)))

(defn get-subscriptions-of-member
  [screen-name]
  (if *skip-api-calls*
    (get {"gpeal8" [754065857257607168 13377222 18904758 116295416 885808123 43692264 771583225466531840 84083187 2448173552 735492984007839744 9453502 121445148 29508165 11317422 5697222 14282054 4300627703 1482581556 1086128414 62516676 1475534683 1224099518 30677296 4812632673 29429203 3023030992 35856700 599908015 505974147 822327888498176003 4061140162 14431523 20875567 705726541 1158494786 911297187664949248 894963430721040384 80687353 805292700 14491079 75954193 1428093824 4994591 2932738834 17530397 1964883696 3064624123 1386025038 8862132 872534744604356608 349286604 14813320 1448328385 18060782 104600630 420308365 923864025682841600 3195467395 221838349 15930040 6639012 18230705 14793247 44936471 167193309 18200589 17609423 46446956 197780707 2975239913 21015156 347963273 36312630 2596803258 4902221 3243052266 16439529 1337348780 1245747631 166315104 15524875 4176991 215369412 1610688912 541145267 39902306 2842668827 1658511 43806249 19087575 291911739 914620160 18904639 2483137124 22238139 19725644 72345613 737622767822999552 106462192 252481460 4686835494 17207733 326511843 836606419965202438 399412477 2742539024 84447535 1247464470 529148230 7758132 267485701 2282138214 239987002 1000799198810341376 37975275 533539874 1032086069775396869 8038312 309528017 117558726 3107896458 3147435697 21745183 28446974 31032895 273375532 774907246731898881 564822845 370885480 492678204 14232727 16431281 1819511 115557940 1463467434 15540222 745273 37103302 6603532 15180856 921398240 1028595288 14475298 257765996 2895499182 876620772 14314572 12960392 439754108 8381682 331639736 779169 16548877 163046484 13235832 16224721 17093431 13861042 976425799984275456 1045579647193419776 575752142 26511350 382703383 14996448 2933441 3915671059 2575413876 137182673 14951823 17055506 5961382 177101260 19325156 15738236 62235143 218411120 400882913 14261086 49755613 22344050 14554494 370844675 2484911062 447073182 169477517 56127471 14569541 831171298705432579 71339900 156746498 734903 383289779 18464354 21375846 113038189 1014560322932404233 18137723 877997162818392064 21333111 2487486942 1226190540 818374871323394048 83642518 2853251879 358151134 144962005 93734534 27930338 19304217 228661919 960499437666734083 41436457 389468789 43275782 126776391 23233607 12691172 706477238 2835456287 16287202 1014199723031449600 224577739 2720877595 14209841 970852134563409921 1044496567229730818 160877807 20733754 19527501 2786900559 2529838104 1567253886 26436421 962779609849774080 53009621 4674033554 222455029 35762667 12381352 781660 759063904421109760 34219149 2157621 79140061 15054648 29550032 901371 9526722 16401507 972644920371458049 4174351036 292591349 448111686 47967842 15839929 935433010475778048 54931832 136933779 646533 14861745 68406693 999685355824697344 3623281 1191207924 22757153 755385795582824448 15101871 70512422 89097023 59482520 3524924897 156417821 7698 15528065 2366811 303800445 15740685 15446348 786720485384056833 115432649 2412232099 888831774528126976 70096167 3343837409 191620154 1602746592 159978634 2811343886 708784997151301633 154143859 13343362 294392319 41628296 414333187 1263573806 71141398 17415957 17028377 2826120037 397932196 17271117 2791147817 3189534807 36158849 752495200300310528 229339862 182855710 15050812 31151313 285012262 1651411842 17929678 255617445 290549888 366355884 3284871420 699463 22279019 748620420224421888 17178315 1675777418 17773446 431988302 131965508 22594744 112520697 157503599 17606481 153190375 717759402 12830832 16452249 11165142 731279155 16160760 770616890062282753 2204296196 815246 14662919 19113589 14552480 14253068 731328740396023809 40829038 384882520 4357362499 3064402186 4180733653 272896062 2292889800 19407394 800044778901618688 3317837519 48667415 280164969 181723645 545432504 4672904180 4514678199 61644598 999021292488658947 528933485 382215113 799616 20374904 12290752 21751427 329025491 123096209 16179055 14683495 416497687 39967597 435310527 1053610074 533409964 1561320768 13889622 48903 69116004 10664812 8250842 225097272 1973042528 1133971 3000230307 2846982741 2352399006 1112465706 2847028078 2151416029 701667727 701625379 570218986 701615052 1076582767 15390783 532923 1671811 35432643 1447284511 14272162 56992811 84967251 15685575 893172070061936640 950354350441984000 16866110 14614648 1112334438 103914540 40144582 12647912 19239448 14148308 6467822 733507289265496064 951199042054344704 917163626238140421 3380448273 2771254832 2306295163 3056085490 2774638535 880570939779555328 3173809816 9453382 750975440144171009 916031611 2784 14364049 29954328 33213413 606659123 187861804 18802551 295218901 32163649 70125496 1399148563 842701386978643969 829334024120307713 3305457991 827372643468734464 726253 34953225 33962758 3247534125 939897540882915328 76980293 2881731465 398339737 2957383612 22353012 2188250509 1237411 404886694 2153638296 43536275 182821975 2410100670 19953639 2312333412 18378342 18375989 181363 2228655612 734805617228054529 2196680173 779361473063555072 460451827 482815096 88619737 263309165 972586638 1740241 303823825 2232194276 1941633097 2243440136 76299434 485285668 875161100513423360 254923763 6647172 749708446090440704 890746289612259328 3401822135 83798722 3278906401 3074425729 2350794840 2318593100 14334846 14860760 397881425 155172780 52672664 2893229143 526200213 875395670567514112 2242379460 700600113966649344 3435712733 2958823554 545697308 153749030 2320955952 42839044 479730548 699271008704983041 107215511 16815643 17710825 796789 354036155 22788858 40289924 420514861 844566513982038017 118917461 794425108072960000 15623102 8892842 700991122139643904 452450028 768537800450854912 851272722 1300994245 103023146 18363942 299753741 256968606 12785742 15094506 127339617 221451423 717677786861805568 8034682 5854882 112018125 12229772 499103692 766905846 289086895 1952726300 545715221 1444239480 28942235 783880164 19395453 1345149312 311334778 3181468092 37400546 1403244217 16599626 2494988861 16167724 31096116 809685 8000842 1355211037 1364688950 141716409 222916327 17744075 1527260750 21392087 352518189 1387081490 311430228 916257050 47313547 1366914511 878102903755427840 376160328 245520831 899723528018292736 96171566 62773661 57266940 2831756688 21500149 248809211 64958324 15500629 44438248 777617540151250944 261141430 18776131 333008109 14084985 3461057292 57580533 1645988304 15405607 1902864992 49367845 788301044992344064 960998965 131014177 171672068 286279116 2327154506 49404693 592933728 11288622 105094958 629645436 1259488068 7814992 2190472483 184458797 12153672 30852791 552053666 21581607 121697127 28117848 3266348924 23934399 194252502 390657992 302519089 390006355 14364083 79579032 3095858509 2883876597 3065925665 15620703 110502774 33270168 930964710 3760227496 3300566902 858043260413071360 224668904 190339667 94820552 20987180 140011523 1012909753 301031798 784755312796499968 14979481 3936977518 763721184089366528 195555022 432054829 2216657444 2482986962 1279376462 2415902426 4896276717 907924568 4830518598 375935537 1536084720 3017474493 4908973983 51454042 13816362 362380525 3176788599 235163374 182962597 14640198 93455698 2682301226 2819726613 1860004692 1301709182 2316910020 4849105353 205393805 78333985 4662119621 97286357 47361215 1231409024 89306611 396731669 4310401179 9992452 2790795553 138135940 4134124649 239047527 35453305 377431232 561160849 14539947 425710535 33272257 2352262861 3222444702 82050764 7483862 240279846 569418080 6663362 1094547996 576591032 312138136 2995822684 36297876 10627012 2340972630 194588510 88221809 16805918 432761366 191441280 66615950 479342644 27454144 362047850 880050606 110212001 22789417 613283229 2878533622 2617357248 32411540 2496138818 214116546 12069152 8851752 14338755 2429238283 90462465 2241308316 2801343906 225638465 547032102 199711660 22604218 55546045 197440751 2785356413 15086612 46956393 272082024 30150841 167390061 2808912273 2740306555 200012382 2596461727 388172391 6120472 2972608827 538544971 2802274409 59442680 43346092 61715213 6994682 28843895 60050283 880915117 1331871536 106528105 191006552 948563334 2688649147 29441684 2280809136 2807284434 8642822 17867828 741686772 1443465937 232241353 166141388 198135636 30004873 329693961 7326882 432585401 2190741 85650836 36081485 221688652 2157441188 15341368 481379877 800272760 19205607 105119237 113098325 1578016110 43728845 220998289 560454290 42432217 41568822 1244656782 16632773 16603929 20141257 796370294 39563842 14474780 391375116 2869355877 2204153172 2205785436 23401141 89689981 21241548 1216591 300186117 2204621110 14756753 783092 227050193 343924441 43508680 238195148 2587234290 15396298 1584035214 37921177 2584046803 2608288363 79172107 130799555 49773293 1305940272 189050046 534096410 789941143 8194472 15070270 16902902 2256136524 125083073 149093406 2405035512 7656672 589143160 561509890 323138380 2575486273 2155956479 46081994 1706912017 15004560 1670797561 2463288750 101370819 2201873430 2277815413 2461002036 17406699 369771033 20065147 50599894 93966147 323222074 832944944 7954512 299111857 133325904 6691032 332945497 273401924 7566122 257188439 2776763911 2384827184 55512579 106036839 384323521 2164962384 572549225 334097857 34573533 1042383080 396019095 2366917016 370621341 25374123 34301872 169851706 372488688 7844432 2195036186 271469324 2150844427 1911812269 235069617 2557481588 172498904 16587559 316358098 49565520 1352958860 264120417 875304660 17907318 209298613 601594751 83576888 16088762 60586403 128226334 19900960 1310382380 292386839 15727643 54175931 113918178 20223904 1919731364 985636939 236037423 247990670 117507933 28536809 38021125 98936006 61834868 19149515 838286762 214023404 100855245 127058221 2354830082 2268465488 371363367 208714598 49413866 9676412 1951821 29517806 17173023 41842768 618243402 947598870 267098317 176103511 323864532 1713902882 257410789 2223370294 14354499 54294888 428641154 210942046 9474872 427678734 85300487 14348674 254060142 51201283 884754084 19976835 167913266 301511943 1868699664 1361334031 1947832249 306379828 18591976 88187595 384547331 42869089 81549237 86278205 67938458 40025815 161811804 113977867 15029643 9298212 65052669 9307922 15370457 38826556 40270562 15787391 41796141 68655992 14637220 15474951 28399610 1300574028 173133259 62772576 562368690 305050237 20771302 406599742 791670 18915289 12658302 198814315 31701852 3893631 41776790 223922112 18850082 14192532 1326552944 96359902 315359509 75250737 749242872 81747730 605236481 8030092 46610577 494503638 48377498 550179258 12779052 956001 22295360 526406603 19043399 14628530 40312608 13076 61145383 22637159 80677920 633273 14961286 614203 201738444 213762543 11628 183472102 139210223 17349787 14114170 40470255 301145266 107633083 18904265]}
      screen-name)
    (:ids (:body (friends-ids :oauth-creds (twitter-credentials)
               :params {:target-screen-name screen-name})))))

(defn get-subscribees-of-member
  [screen-name]
  (if *skip-api-calls*
    {}
    (:ids (:body (followers-ids :oauth-creds (twitter-credentials)
                 :params {:target-screen-name screen-name})))))