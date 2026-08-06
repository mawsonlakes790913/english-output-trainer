--
-- PostgreSQL database dump
--

\restrict MDsJaXljfGs2symb8P0sLLixuRlErcIWZ4SAO6hk6POUOsjoK4kfTulItbTGJ3G

-- Dumped from database version 18.3
-- Dumped by pg_dump version 18.3

-- Started on 2026-08-07 03:19:21 JST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3845 (class 0 OID 30446)
-- Dependencies: 220
-- Data for Name: question; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.question (question_id, japanese_text, english_text, alternative_answer, condition, difficulty) FROM stdin;
420	警察はその可能性を除外しました。	The police ruled out that possibility.	The police eliminated that possibility.	\N	ADVANCED
758	この問題は間もなく削除されることになっています。	This question is supposed to be deleted before long.		受動態	ADVANCED
201	会議は予定より30分早く始まりました。	The meeting began thirty minutes earlier than scheduled.	The meeting started thirty minutes earlier than scheduled.	\N	INTERMEDIATE
202	私は重要な書類を誤って削除してしまいました。	I accidentally deleted an important document.	I accidentally removed an important document.	\N	INTERMEDIATE
203	彼は普通の車ではなく高級車を購入しました。	He purchased a luxury car instead of an ordinary one.	He bought a luxury car instead of a regular one.	purchase	INTERMEDIATE
204	私は健康のために砂糖を控えるようにしています。	I try to avoid eating too much sugar for my health.	I try not to eat too much sugar for my health.	動名詞	INTERMEDIATE
205	このホテルは価格の割にはかなり快適でした。	This hotel was quite comfortable for the price.	The hotel was very comfortable considering the price.	\N	INTERMEDIATE
206	彼女は海外で働く機会を探しています。	She is looking for an opportunity to work abroad.	She is searching for a chance to work abroad.	不定詞	INTERMEDIATE
207	私はその提案について少し考える時間が必要です。	I need some time to consider your suggestion.	I need some time to think about your suggestion.	\N	INTERMEDIATE
208	彼は十分な経験があるので心配する必要はありません。	He has sufficient experience, so there is no need to worry.	He has enough experience, so there is no need to worry.	\N	INTERMEDIATE
209	その知らせは私たち全員を驚かせました。	The news surprised all of us.	All of us were surprised by the news.	無生物主語	INTERMEDIATE
244	私は彼に率直な意見を伝えました。	I gave him my honest opinion.	I told him my honest opinion.	\N	INTERMEDIATE
401	警察は現場で偽造パスポートを押収しました。	The police confiscated a fake passport at the scene.	The police seized a fake passport at the scene.	\N	ADVANCED
402	私の席は窓の隣です。	My seat is adjacent to the window.	My seat is next to the window.	\N	ADVANCED
403	乾いた草はすぐに燃え始めました。	The dry grass ignited within a few seconds.	The dry grass caught fire within a few seconds.	\N	ADVANCED
404	彼は重大犯罪で起訴されました。	He was charged with a felony.	He was accused of a felony.	受動態	ADVANCED
405	そのうわさは数年後に再び広まりました。	The rumor resurfaced several years later.	The rumor appeared again several years later.	\N	ADVANCED
406	その結果には非常に満足しています。	I am supremely satisfied with the result.	I am extremely satisfied with the result.	\N	ADVANCED
407	最近、一連のトラブルが発生しています。	We have experienced a string of problems recently.	We have had a series of problems recently.	現在完了	ADVANCED
408	彼はどこからともなく現れました。	He appeared out of nowhere.	He suddenly appeared.	\N	ADVANCED
409	私は目の端で誰かが動くのを見ました。	I noticed someone moving out of the corner of my eye.	I saw someone moving from the corner of my eye.	\N	ADVANCED
410	警備員はその建物を監視していました。	The guard kept the building under observation.	The guard watched the building carefully.	\N	ADVANCED
411	彼は新しいルールを実施することにしました。	He decided to implement the new policy.	He decided to carry out the new policy.	不定詞	ADVANCED
412	私は彼の沈黙を賛成だと解釈しました。	I interpreted his silence as agreement.	I understood his silence as agreement.	\N	ADVANCED
413	その事故は避けられなかったようです。	The accident seemed inevitable.	The accident seemed impossible to avoid.	\N	ADVANCED
414	彼女はその仕事を引き受けることに消極的でした。	She was reluctant to accept the task.	She was unwilling to accept the task.	不定詞	ADVANCED
415	その変更は大きな改善につながりました。	The change brought substantial improvement.	The change brought major improvement.	\N	ADVANCED
416	私は重要なメッセージを見落としていました。	I had overlooked an important message.	I had missed an important message.	過去完了	ADVANCED
417	彼は会議で新しい案を提案しました。	He came up with a new idea at the meeting.	He thought of a new idea at the meeting.	\N	ADVANCED
418	私たちはその計画を予定どおり実行しました。	We carried out the plan as scheduled.	We executed the plan as scheduled.	\N	ADVANCED
419	彼女は問題の原因を突き止めました。	She figured out the cause of the problem.	She found the cause of the problem.	\N	ADVANCED
464	その会社は急速に事業を拡大しました。	The company rapidly expanded its business.	The company quickly expanded its business.	\N	ADVANCED
465	私はその出来事を今でも鮮明に覚えています。	I still remember the event vividly.	I can still remember the event clearly.	\N	ADVANCED
466	彼はその仕事を効率よく進めました。	He handled the task efficiently.	He managed the task efficiently.	\N	ADVANCED
467	私たちはその制度を徐々に改善しました。	We gradually refined the system.	We gradually improved the system.	\N	ADVANCED
468	彼女は重要な情報を正確に記録しました。	She recorded the important information precisely.	She recorded the important information accurately.	\N	ADVANCED
469	私は彼の意図を完全には理解できませんでした。	I could not fully grasp his intention.	I could not completely understand his intention.	\N	ADVANCED
470	その会議は予定より長く続きました。	The meeting lasted longer than anticipated.	The meeting lasted longer than expected.	比較	ADVANCED
550	そのミスは会社全体を危険にさらすかもしれません。	I think this mistake could jeopardize the entire company.	I think this mistake might jeopardize the entire company.	\N	ADVANCED
551	私はその計画は実現可能だと思います。	I think the plan is feasible.	I believe the plan is feasible.	\N	ADVANCED
598	新しい問題が突然発生しました。	A new issue emerged this morning.	A new issue appeared this morning.	\N	ADVANCED
1	私は昨日、駅で高校時代の友人に偶然会いました。	I happened to meet a friend from high school at the station yesterday.	I ran into a friend from high school at the station yesterday.	不定詞	BEGINNER
2	彼女は健康を維持するために毎朝30分歩いています。	She walks for thirty minutes every morning to stay healthy.	She takes a thirty-minute walk every morning to stay healthy.	不定詞	BEGINNER
3	私はその映画を見て泣かずにはいられませんでした。	I could not help crying after watching the movie.	I couldn't stop crying after watching the movie.	動名詞	BEGINNER
4	彼は今まで一度も海外へ行ったことがありません。	He has never been abroad in his life.	He has never traveled overseas.	現在完了	BEGINNER
5	この橋は100年以上前に建てられました。	This bridge was built more than one hundred years ago.	\N	受動態	BEGINNER
6	私の兄は私より3歳年上です。	My brother is three years older than I am.	My brother is three years older than me.	比較	BEGINNER
7	昨日読んだ本はとても面白かったです。	The book that I read yesterday was very interesting.	The book which I read yesterday was very interesting.	関係代名詞	BEGINNER
8	もっと早く家を出るべきだったと思います。	I think I should have left home much earlier.	I believe I should have left home earlier.	助動詞	BEGINNER
9	この仕事は一人で終わらせるには難しすぎます。	This work is too difficult to finish by myself.	This job is too difficult to complete by myself.	too...to	BEGINNER
10	彼女は私が期待していたよりもずっと親切でした。	She was much kinder than I had expected.	She was far kinder than I had expected.	比較	BEGINNER
11	雨のせいで私たちは試合を延期しなければなりませんでした。	The rain made us postpone the game.	We had to postpone the game because of the rain.	使役動詞	BEGINNER
12	私は彼に真実を話すことを決心しました。	I decided to tell him the truth.	I made up my mind to tell him the truth.	不定詞	BEGINNER
13	彼は忙しかったにもかかわらず、私を手伝ってくれました。	Although he was busy, he helped me.	Though he was busy, he helped me.	\N	BEGINNER
14	彼女は宿題を終えてからテレビを見ました。	She watched TV after finishing her homework.	She watched television after finishing her homework.	動名詞	BEGINNER
15	このコンピューターは私が思っていたほど高くありませんでした。	This computer was not as expensive as I had expected.	This computer wasn't as expensive as I expected.	as...as	BEGINNER
16	彼が何を言ったのか理解できませんでした。	I could not understand what he said.	I couldn't understand what he was saying.	間接疑問文	BEGINNER
17	私はあなたにもう一度その質問をしてほしいです。	I want you to ask me that question again.	I would like you to ask me that question again.	不定詞	BEGINNER
18	彼女は外国で働くことに興味があります。	She is interested in working abroad.	She is interested in getting a job abroad.	動名詞	BEGINNER
19	この部屋は毎日掃除されています。	This room is cleaned every day.	\N	受動態	BEGINNER
730	私たちはその問題を慎重に対処しました。	We addressed the issue carefully.	We dealt with the issue carefully.	\N	ADVANCED
20	もし私がお金持ちなら、世界中を旅行するでしょう。	If I were rich, I would travel around the world.	\N	仮定法	BEGINNER
21	彼は私にその箱を運ぶのを手伝ってくれました。	He helped me carry the box.	He helped me to carry the box.	使役・原形不定詞	BEGINNER
22	彼女は疲れていたので早く寝ました。	She went to bed early because she was tired.	She went to bed early since she was tired.	\N	BEGINNER
23	私は駅に着いたとき、電車はすでに出発していました。	The train had already left when I arrived at the station.	\N	過去完了	BEGINNER
24	彼が成功したという知らせは私を驚かせました。	The news that he had succeeded surprised me.	The news of his success surprised me.	関係代名詞	BEGINNER
25	私はこの問題をどう解決すればよいのかわかりません。	I do not know how to solve this problem.	I don't know how I should solve this problem.	疑問詞+不定詞	BEGINNER
26	あなたは約束を守るべきです。	You should keep your promise no matter what happens.	You ought to keep your promise no matter what happens.	助動詞	BEGINNER
27	私は彼女にその仕事を手伝ってくれるよう頼みました。	I asked her to help me with the work.	I asked her to help with the work.	不定詞	BEGINNER
28	彼は英語だけでなく中国語も話すことができます。	He can speak not only English but also Chinese.	He is able to speak not only English but also Chinese.	相関表現	BEGINNER
29	この町には訪れる価値のある場所がたくさんあります。	There are many places worth visiting in this town.	There are many places that are worth visiting in this town.	動名詞	BEGINNER
30	私は財布をなくしたことに帰宅してから気付きました。	I realized I had lost my wallet after I got home.	I found that I had lost my wallet after I got home.	過去完了	BEGINNER
31	彼女は私に窓を閉めるように言いました。	She told me to close the window.	She asked me to close the window.	不定詞	BEGINNER
32	私たちは暗くなる前にホテルへ着くことができました。	We were able to reach the hotel before it got dark.	We managed to reach the hotel before it got dark.	助動詞	BEGINNER
33	彼はとても疲れていたので夕食も食べずに寝ました。	He was so tired that he went to bed without dinner.	He was so tired that he went to bed without eating dinner.	so...that	BEGINNER
34	私はこの写真を見るたびに子どもの頃を思い出します。	Whenever I see this picture, I remember my childhood.	Every time I see this picture, I remember my childhood.	\N	BEGINNER
35	彼女は医者になるという夢をあきらめませんでした。	She did not give up her dream of becoming a doctor.	She never gave up her dream of becoming a doctor.	動名詞	BEGINNER
36	私が驚いたことに、彼はその試験に合格しました。	To my surprise, he passed the exam.	Surprisingly, he passed the exam.	慣用表現	BEGINNER
37	あなたはそんなに急ぐ必要はありません。	You do not have to hurry so much.	You don't need to hurry so much.	助動詞	BEGINNER
38	彼は誰よりも速くその問題を解きました。	He solved the problem faster than anyone else.	He solved the problem the fastest of all.	比較	BEGINNER
39	彼女は私に笑顔で話しかけてくれました。	She spoke to me with a smile on her face.	She talked to me with a smile on her face.	\N	BEGINNER
40	私は忙しすぎて彼に電話できませんでした。	I was too busy to call him.	I was so busy that I could not call him.	too...to	BEGINNER
41	この町は以前よりずっとにぎやかになりました。	This town has become much livelier than before.	This town has become much more lively than before.	現在完了	BEGINNER
42	彼は私に駅まで車で送ってくれました。	He gave me a ride to the station.	He drove me to the station.	\N	BEGINNER
43	彼女が言ったことは私には信じられませんでした。	I could not believe what she said.	I couldn't believe what she had said.	間接疑問文	BEGINNER
44	私たちは雨にもかかわらず試合を続けました。	We continued the game despite the rain.	We went on with the game despite the rain.	\N	BEGINNER
45	彼はその知らせを聞いてとても安心しました。	He felt very relieved when he heard the news.	He was very relieved to hear the news.	不定詞	BEGINNER
46	私は子どもの頃、この川でよく泳いでいました。	I used to swim in this river when I was a child.	I would often swim in this river when I was a child.	used to	BEGINNER
47	彼女はそのバッグを買うのに十分なお金を持っています。	She has enough money to buy the bag.	She has enough money for the bag.	enough to	BEGINNER
48	この本は多くの国で読まれています。	This book is read in many countries.	This book has been read in many countries.	受動態	BEGINNER
49	私は彼が正しいと思います。	I think that he is right.	I believe that he is right.	\N	BEGINNER
50	もし昨日もっと早く寝ていたら、今日はもっと元気だったでしょう。	If I had gone to bed earlier yesterday, I would feel better today.	If I had gone to bed earlier, I would be feeling better today.	仮定法	BEGINNER
51	彼は試験に合格するために毎日遅くまで勉強しています。	He studies until late every day to pass the exam.	He studies late every day in order to pass the exam.	不定詞	BEGINNER
52	私は駅へ向かう途中で財布を落としました。	I lost my wallet on my way to the station.	I dropped my wallet on the way to the station.	\N	BEGINNER
53	彼女は友達に勧められてその本を読みました。	She read the book because her friend recommended it.	She read the book after her friend recommended it.	\N	BEGINNER
54	私はその知らせを聞いて安心しました。	I was relieved to hear the news.	I felt relieved when I heard the news.	不定詞	BEGINNER
55	彼は子どもの頃からずっとサッカーをしています。	He has played soccer since he was a child.	He has been playing soccer since he was a child.	現在完了	BEGINNER
56	この部屋では英語だけを話さなければなりません。	You must speak only English in this room.	You have to speak only English in this room.	助動詞	BEGINNER
57	私は彼女ほど上手にピアノを弾くことができません。	I cannot play the piano as well as she can.	I can't play the piano as well as she does.	as...as	BEGINNER
58	その店は先月新しくオープンしました。	The shop opened last month.	The store opened last month.	\N	BEGINNER
59	彼は誰にも何も言わずに部屋を出て行きました。	He left the room without saying anything to anyone.	He went out of the room without saying a word.	動名詞	BEGINNER
60	私は昨日、学校で先生にほめられました。	I was praised by my teacher at school yesterday.	My teacher praised me at school yesterday.	受動態	BEGINNER
61	私の父は毎朝新聞を読む習慣があります。	My father has a habit of reading the newspaper every morning.	My father usually reads the newspaper every morning.	動名詞	BEGINNER
62	この問題は私には難しすぎて解けません。	This problem is too difficult for me to solve.	This problem is so difficult that I cannot solve it.	too...to	BEGINNER
63	彼女は疲れていたにもかかわらず最後まで働きました。	She worked until the end although she was tired.	She worked until the end even though she was tired.	\N	BEGINNER
64	私たちは電車に間に合うように急ぎました。	We hurried to catch the train.	We rushed to catch the train.	不定詞	BEGINNER
65	私はその映画を二回見たことがあります。	I have seen the movie twice.	I have watched the movie twice.	現在完了	BEGINNER
66	彼は医者になることを夢見ています。	He dreams of becoming a doctor.	He dreams about becoming a doctor.	動名詞	BEGINNER
67	私が到着したとき、会議はすでに始まっていました。	The meeting had already started when I arrived.	The meeting had already begun when I arrived.	過去完了	BEGINNER
68	彼女は私に駅への行き方を教えてくれました。	She told me how to get to the station.	She showed me how to get to the station.	疑問詞+不定詞	BEGINNER
69	彼はその仕事を一人で終わらせることができました。	He was able to finish the work by himself.	He managed to finish the work by himself.	助動詞	BEGINNER
70	私たちは雨が止むまでここで待つつもりです。	We will wait here until the rain stops.	We are going to wait here until the rain stops.	\N	BEGINNER
71	彼女は誰よりも速く泳ぐことができます。	She can swim faster than anyone else.	She is the fastest swimmer of all.	比較	BEGINNER
72	私は英語を話す機会をもっと増やしたいです。	I want more opportunities to speak English.	I would like to have more chances to speak English.	不定詞	BEGINNER
73	この公園では多くの子どもたちが遊んでいました。	Many children were playing in this park.	A lot of children were playing in the park.	\N	BEGINNER
74	彼は忙しかったので私たちの誘いを断りました。	He turned down our invitation because he was busy.	He refused our invitation because he was busy.	\N	BEGINNER
75	もし私にもっと時間があれば、その本を読むのですが。	If I had more time, I would read the book.	If I had enough time, I would read that book.	仮定法	BEGINNER
76	私たちは来月、新しいプロジェクトを始める予定です。	We are going to start a new project next month.	We will start a new project next month.	\N	BEGINNER
77	彼女は朝食を食べずに学校へ行きました。	She went to school without eating breakfast.	She went to school without having breakfast.	動名詞	BEGINNER
78	私はその知らせを聞いた瞬間、とても驚きました。	I was very surprised the moment I heard the news.	I was shocked as soon as I heard the news.	\N	BEGINNER
79	彼は外国で働くことを真剣に考えています。	He is seriously thinking about working abroad.	He is considering working abroad seriously.	動名詞	BEGINNER
80	私はこの町に十年以上住んでいます。	I have lived in this town for more than ten years.	I have been living in this town for over ten years.	現在完了	BEGINNER
81	その建物は地震でひどく壊されました。	The building was badly damaged by the earthquake.	\N	受動態	BEGINNER
82	彼女は宿題を終えるとすぐに友達へ電話しました。	She called her friend as soon as she finished her homework.	She phoned her friend right after finishing her homework.	\N	BEGINNER
83	私は父に車を洗うように言われました。	I was told to wash the car by my father.	My father told me to wash the car.	受動態	BEGINNER
84	このバッグは毎日使うには十分軽いです。	This bag is light enough to use every day.	This bag is light enough for daily use.	enough to	BEGINNER
85	彼はその仕事を時間どおりに終えられませんでした。	He could not finish the work on time.	He wasn't able to finish the work on time.	助動詞	BEGINNER
86	私は彼女ほど忍耐強くありません。	I am not as patient as she is.	I'm not as patient as she is.	as...as	BEGINNER
87	先生は私たちに静かにするよう注意しました。	The teacher told us to be quiet.	The teacher asked us to keep quiet.	不定詞	BEGINNER
88	この店は週末になるといつも混雑しています。	This store is always crowded on weekends.	This shop is always busy on weekends.	受動態	BEGINNER
89	私は電車を待っている間、本を読んでいました。	I was reading a book while waiting for the train.	I read a book while I was waiting for the train.	動名詞	BEGINNER
90	彼は私が思っていたよりずっと若く見えました。	He looked much younger than I had expected.	He looked far younger than I had expected.	比較	BEGINNER
91	私たちは先生の説明を注意深く聞きました。	We listened carefully to the teacher's explanation.	We listened to the teacher's explanation carefully.	\N	BEGINNER
92	彼女は健康のために甘い物を控えています。	She avoids eating sweets for her health.	She tries not to eat sweets for her health.	動名詞	BEGINNER
93	私はその会議に参加できてうれしかったです。	I was happy to attend the meeting.	I was glad to take part in the meeting.	不定詞	BEGINNER
94	彼は私にその秘密を誰にも話さないよう頼みました。	He asked me not to tell anyone the secret.	He asked me to keep the secret to myself.	不定詞	BEGINNER
95	もしあなたが私の立場なら、どうしますか。	If you were in my position, what would you do?	If you were in my shoes, what would you do?	仮定法	BEGINNER
96	この問題は見た目ほど難しくありません。	This problem is not as difficult as it looks.	This problem isn't as hard as it seems.	as...as	BEGINNER
97	私は帰宅するとすぐに犬の散歩に行きました。	I took my dog for a walk as soon as I got home.	I walked my dog as soon as I arrived home.	\N	BEGINNER
98	彼女は忙しいにもかかわらず、私の相談に乗ってくれました。	Although she was busy, she gave me some advice.	Even though she was busy, she gave me advice.	\N	BEGINNER
99	彼は毎日練習しているので着実に上達しています。	He is improving steadily because he practices every day.	He keeps improving because he practices every day.	現在進行形	BEGINNER
100	その経験は私に英語を学ぶことの大切さを教えてくれました。	The experience taught me the importance of learning English.	The experience showed me how important it is to learn English.	無生物主語	BEGINNER
101	私たちは予定より30分早く空港に到着しました。	We arrived at the airport thirty minutes earlier than scheduled.	We got to the airport thirty minutes ahead of schedule.	比較	BEGINNER
102	私は彼の説明を聞いてようやくその問題を理解しました。	I finally understood the problem after listening to his explanation.	I finally understood the problem after hearing his explanation.	\N	BEGINNER
103	彼女は子どもたちに本を読むことの大切さを教えています。	She teaches children the importance of reading books.	She teaches children how important reading books is.	動名詞	BEGINNER
104	私たちは悪天候のため試合を中止しました。	We canceled the game because of the bad weather.	We called off the game because of the bad weather.	\N	BEGINNER
105	彼は英語を上達させるために毎日ニュースを読んでいます。	He reads the news every day to improve his English.	He reads news articles every day to improve his English.	不定詞	BEGINNER
106	私はこんなに美しい景色を見たことがありません。	I have never seen such a beautiful view.	I have never seen a view this beautiful.	現在完了	BEGINNER
107	彼女は私に駅で待つように言いました。	She told me to wait at the station.	She asked me to wait at the station.	不定詞	BEGINNER
108	この川では夏になると多くの人が泳ぎます。	Many people swim in this river during the summer.	A lot of people swim in this river in summer.	\N	BEGINNER
109	私は鍵をなくしたので家に入れませんでした。	I could not enter my house because I had lost my key.	I couldn't get into my house because I had lost my key.	過去完了	BEGINNER
110	彼は宿題を終える前にゲームを始めました。	He started playing games before finishing his homework.	He began playing games before he finished his homework.	動名詞	BEGINNER
111	その映画は私が期待していたより面白かったです。	The movie was more interesting than I had expected.	The movie was better than I had expected.	比較	BEGINNER
112	彼女は私に写真を撮ってくれるよう頼みました。	She asked me to take a picture of her.	She asked me to take her picture.	不定詞	BEGINNER
113	私はできるだけ早くこの仕事を終えたいです。	I want to finish this work as soon as possible.	I would like to finish this work as soon as possible.	不定詞	BEGINNER
114	彼は財布を拾って警察へ届けました。	He found a wallet and took it to the police.	He picked up a wallet and brought it to the police.	\N	BEGINNER
115	私の祖父は80歳ですが、今でも毎日運動しています。	My grandfather is eighty years old, but he still exercises every day.	My grandfather is eighty, but he still works out every day.	\N	BEGINNER
116	この問題は一人で解決するには難しすぎます。	This problem is too difficult to solve by yourself.	This problem is too hard for one person to solve.	too...to	BEGINNER
117	私は彼が約束を守ると信じています。	I believe that he will keep his promise.	I believe he will keep his word.	\N	BEGINNER
118	もし明日晴れたら、公園へ行きましょう。	If it is sunny tomorrow, let's go to the park.	If tomorrow is sunny, let's go to the park.	条件節	BEGINNER
119	彼女は外国人と話す機会を探しています。	She is looking for a chance to talk with foreigners.	She is looking for an opportunity to speak with foreigners.	不定詞	BEGINNER
120	先生は生徒たちにもっと質問するよう勧めました。	The teacher encouraged the students to ask more questions.	The teacher advised the students to ask more questions.	不定詞	BEGINNER
121	私は疲れていたので夕食後すぐ寝ました。	I went to bed right after dinner because I was tired.	I went to sleep right after dinner because I was tired.	\N	BEGINNER
122	その橋は地元の人々によって100年前に造られました。	The bridge was built by local people one hundred years ago.	\N	受動態	BEGINNER
123	私は駅に着いたとき、バスはちょうど出発するところでした。	The bus was just leaving when I arrived at the station.	The bus was about to leave when I arrived at the station.	過去進行形	BEGINNER
124	彼は毎日努力しているので必ず成功するでしょう。	He works hard every day, so he will surely succeed.	He works hard every day, so he is sure to succeed.	\N	BEGINNER
125	私たちは彼女の成功を心から祝いました。	We sincerely celebrated her success.	We congratulated her on her success.	\N	BEGINNER
126	私はその知らせを聞いて本当に安心しました。	I was really relieved to hear the news.	I felt really relieved when I heard the news.	不定詞	BEGINNER
127	彼は毎日ジョギングをして健康を保っています。	He goes jogging every day to stay healthy.	He jogs every day to keep himself healthy.	不定詞	BEGINNER
128	私は昨日借りた本をまだ読み終えていません。	I have not finished reading the book I borrowed yesterday.	I haven't finished the book I borrowed yesterday.	現在完了	BEGINNER
129	彼女はその質問にすぐ答えることができませんでした。	She could not answer the question immediately.	She wasn't able to answer the question right away.	助動詞	BEGINNER
130	このレストランは友達に勧められました。	This restaurant was recommended by my friend.	My friend recommended this restaurant to me.	受動態	BEGINNER
131	私は子どもの頃、毎日この公園で遊んでいました。	I used to play in this park every day as a child.	I would play in this park every day as a child.	used to	BEGINNER
132	彼は誰よりも熱心にそのプロジェクトに取り組みました。	He worked on the project more enthusiastically than anyone else.	He worked harder on the project than anyone else.	比較	BEGINNER
133	私は彼女にもう一度説明してくれるよう頼みました。	I asked her to explain it again.	I asked her to explain it to me again.	不定詞	BEGINNER
134	彼女は私が思っていたほど緊張していませんでした。	She was not as nervous as I had expected.	She wasn't as nervous as I expected.	as...as	BEGINNER
135	私は駅へ急いだのに電車に乗り遅れました。	Although I hurried to the station, I missed the train.	Even though I hurried to the station, I missed the train.	\N	BEGINNER
136	彼は外国で働く夢を実現しました。	He achieved his dream of working abroad.	He made his dream of working abroad come true.	動名詞	BEGINNER
137	私は彼が言ったことを信じることができませんでした。	I could not believe what he had said.	I couldn't believe what he said.	過去完了	BEGINNER
138	私たちは出発する前に昼食を食べました。	We had lunch before leaving.	We ate lunch before we left.	動名詞	BEGINNER
139	この本は英語を勉強している人に人気があります。	This book is popular with people studying English.	This book is popular among English learners.	分詞	BEGINNER
140	私は彼女ほど速く走れません。	I cannot run as fast as she can.	I can't run as fast as she does.	as...as	BEGINNER
141	その知らせでみんなが笑顔になりました。	The news made everyone smile.	The news made everybody smile.	使役動詞	BEGINNER
142	もし私があなたなら、その仕事を引き受けます。	If I were you, I would accept the job.	If I were in your position, I would take the job.	仮定法	BEGINNER
143	彼女は毎日英語の日記を書くようにしています。	She tries to write an English diary every day.	She tries to keep a diary in English every day.	不定詞	BEGINNER
144	私は彼に会えてとてもうれしかったです。	I was very happy to see him.	I was very glad to meet him.	不定詞	BEGINNER
145	私たちは予定どおり会議を始めました。	We started the meeting as scheduled.	We began the meeting as planned.	\N	BEGINNER
146	彼は自分の間違いに気付いて謝りました。	He realized his mistake and apologized.	He noticed his mistake and apologized.	\N	BEGINNER
147	私は昨日より今日の方がずっと忙しいです。	I am much busier today than I was yesterday.	I'm far busier today than yesterday.	比較	BEGINNER
148	彼女は何時間も勉強した後、ようやく休憩しました。	She finally took a break after studying for hours.	She took a break after she had studied for hours.	動名詞	BEGINNER
149	この部屋は子どもたちによって毎日掃除されています。	This room is cleaned by the children every day.	The children clean this room every day.	受動態	BEGINNER
150	私は英語を話す自信を少しずつ持てるようになりました。	I have gradually become confident in speaking English.	I have gradually gained confidence in speaking English.	現在完了	BEGINNER
151	私は駅へ向かう途中で昔の先生に会いました。	I met my former teacher on my way to the station.	I ran into my former teacher on my way to the station.	\N	BEGINNER
152	彼は私より英語を話すのが上手です。	He speaks English better than I do.	He is better at speaking English than I am.	比較	BEGINNER
153	私は昨日買ったカメラでたくさん写真を撮りました。	I took many pictures with the camera I bought yesterday.	I took many photos with the camera I bought yesterday.	関係代名詞	BEGINNER
154	彼女は一人でその問題を解くことができました。	She was able to solve the problem by herself.	She managed to solve the problem by herself.	助動詞	BEGINNER
155	私は彼に遅刻しないように伝えました。	I told him not to be late.	I asked him not to be late.	不定詞	BEGINNER
156	その本は私が今まで読んだ中で最も面白い本です。	This is the most interesting book I have ever read.	This is the best book I have ever read.	現在完了	BEGINNER
157	彼女は試験に合格するために一生懸命勉強しました。	She studied hard to pass the exam.	She studied hard in order to pass the exam.	不定詞	BEGINNER
158	私は家を出たとき、雨が降り始めました。	It started raining when I left home.	It began to rain when I left home.	\N	BEGINNER
159	彼は子どもの頃からピアノを習っています。	He has learned the piano since he was a child.	He has played the piano since he was a child.	現在完了	BEGINNER
160	この橋は毎日何千人もの人に利用されています。	This bridge is used by thousands of people every day.	Thousands of people use this bridge every day.	受動態	BEGINNER
161	私は宿題を終えてから友達と遊びに行きました。	I went to play with my friends after finishing my homework.	I went to see my friends after finishing my homework.	動名詞	BEGINNER
162	彼女は私が思っていたよりずっと背が高かったです。	She was much taller than I had expected.	She was far taller than I had expected.	比較	BEGINNER
163	私は彼に英語を教えてもらっています。	I am taught English by him.	He teaches me English.	受動態	BEGINNER
164	私たちは夕食のあとで散歩に出かけました。	We went for a walk after dinner.	We took a walk after dinner.	\N	BEGINNER
165	彼は疲れていたのでソファで眠ってしまいました。	He was so tired that he fell asleep on the sofa.	He was so tired that he went to sleep on the sofa.	so...that	BEGINNER
166	私は彼女に真実を話すべきだったと思います。	I think I should have told her the truth.	I believe I should have told her the truth.	助動詞	BEGINNER
167	もし今日が日曜日なら、家でゆっくり休むのですが。	If today were Sunday, I would relax at home.	If today were Sunday, I would stay home and relax.	仮定法	BEGINNER
168	このバッグは旅行に持って行くのに十分小さいです。	This bag is small enough to take on a trip.	This bag is small enough for traveling.	enough to	BEGINNER
169	私は彼が約束を守ると確信しています。	I am sure that he will keep his promise.	I am certain that he will keep his word.	\N	BEGINNER
170	彼女は窓の外を見ながら音楽を聴いていました。	She was listening to music while looking out the window.	She listened to music while looking out of the window.	動名詞	BEGINNER
171	私はこの問題を解くのに30分かかりました。	It took me thirty minutes to solve this problem.	I spent thirty minutes solving this problem.	無生物主語	BEGINNER
172	彼はその知らせを聞くとすぐに両親へ電話しました。	He called his parents as soon as he heard the news.	He phoned his parents immediately after hearing the news.	\N	BEGINNER
173	私は外国人の友達をもっと作りたいです。	I want to make more foreign friends.	I would like to make more friends from other countries.	不定詞	BEGINNER
174	この映画は子どもだけでなく大人にも人気があります。	This movie is popular with not only children but also adults.	This movie is popular among both children and adults.	相関表現	BEGINNER
175	彼女は毎日少しずつ英語力を伸ばしています。	She is improving her English little by little every day.	Her English is improving little by little every day.	現在進行形	BEGINNER
176	私は朝起きるとまずコップ一杯の水を飲みます。	I drink a glass of water first when I get up.	The first thing I do after getting up is drink a glass of water.	\N	BEGINNER
177	彼は忙しかったので私たちのパーティーに来られませんでした。	He could not come to our party because he was busy.	He was unable to come to our party because he was busy.	助動詞	BEGINNER
178	私は彼女が昨日言ったことをまだ覚えています。	I still remember what she said yesterday.	I still remember what she told me yesterday.	間接疑問文	BEGINNER
179	この仕事を終えるにはあと二時間必要です。	It will take two more hours to finish this work.	We need two more hours to finish this work.	無生物主語	BEGINNER
180	彼は高校を卒業してから東京に住んでいます。	He has lived in Tokyo since he graduated from high school.	He has been living in Tokyo since graduating from high school.	現在完了	BEGINNER
181	私は父に新しい自転車を買ってもらいました。	My father bought me a new bicycle.	I got a new bicycle from my father.	\N	BEGINNER
182	彼女は私が困っていることにすぐ気付きました。	She noticed that I was in trouble right away.	She realized that I was having trouble right away.	\N	BEGINNER
183	私たちは暗くなる前に山を下りました。	We came down the mountain before it got dark.	We climbed down the mountain before it became dark.	\N	BEGINNER
184	彼は何時間も練習したので自信を持っていました。	He felt confident because he had practiced for hours.	He was confident because he had practiced for a long time.	過去完了	BEGINNER
185	私は英語を勉強すればするほど楽しくなります。	The more I study English, the more enjoyable it becomes.	The more I study English, the more fun it becomes.	比較	BEGINNER
186	彼女はそのプレゼントを見てとても喜びました。	She was very happy to see the present.	She was delighted to receive the present.	不定詞	BEGINNER
187	この歌は世界中の人々に愛されています。	This song is loved by people all over the world.	People all over the world love this song.	受動態	BEGINNER
188	私は彼に手伝ってくれたことを感謝しました。	I thanked him for helping me.	I thanked him because he helped me.	動名詞	BEGINNER
189	その会議は予定より早く終わりました。	The meeting ended earlier than expected.	The meeting finished earlier than we expected.	比較	BEGINNER
190	彼は昼食を食べずに仕事を続けました。	He continued working without eating lunch.	He kept working without having lunch.	動名詞	BEGINNER
191	私は彼女にもう少しゆっくり話してほしいです。	I want her to speak a little more slowly.	I would like her to speak a little more slowly.	不定詞	BEGINNER
192	もし私がもっと若ければ、その仕事に応募するでしょう。	If I were younger, I would apply for the job.	If I were a little younger, I would apply for that job.	仮定法	BEGINNER
193	彼は私に駅までの一番近い道を教えてくれました。	He showed me the shortest way to the station.	He told me the quickest way to the station.	\N	BEGINNER
194	私は雨が止むまでカフェで待っていました。	I waited at a café until the rain stopped.	I stayed at a café until the rain stopped.	\N	BEGINNER
195	彼女は新しい環境にすぐ慣れました。	She quickly got used to her new environment.	She quickly became used to her new environment.	get used to	BEGINNER
196	私はその知らせを聞いて安心すると同時に驚きました。	I was surprised and relieved when I heard the news.	I felt both surprised and relieved when I heard the news.	\N	BEGINNER
197	彼はその本を読み終えるのに一週間かかりました。	It took him a week to finish reading the book.	He spent a week finishing the book.	無生物主語	BEGINNER
198	私は毎日英語のポッドキャストを聞くようにしています。	I try to listen to English podcasts every day.	I try to listen to a podcast in English every day.	不定詞	BEGINNER
199	彼女は試験の結果に満足していませんでした。	She was not satisfied with the result of the exam.	She wasn't satisfied with her exam result.	\N	BEGINNER
200	私たちは彼の成功をみんなで祝いました。	We all celebrated his success together.	We all congratulated him on his success.	\N	BEGINNER
210	私は出張中に新しい友人を何人か作りました。	I made several new friends during my business trip.	I met several new friends during my business trip.	\N	INTERMEDIATE
211	彼女はその仕事を引き受けるかどうかまだ決めていません。	She has not decided whether to accept the job yet.	She hasn't decided whether she will accept the job yet.	現在完了	INTERMEDIATE
212	このレストランは地元の人たちにも人気があります。	This restaurant is popular with local people as well.	This restaurant is also popular among local people.	\N	INTERMEDIATE
213	私は以前ほど夜更かしをしなくなりました。	I do not stay up as late as I used to.	I don't stay up late like I used to.	比較	INTERMEDIATE
214	彼は問題を解決するために専門家へ相談しました。	He consulted an expert to solve the problem.	He asked an expert for advice to solve the problem.	不定詞	INTERMEDIATE
215	この機械は操作するのが思ったより簡単でした。	This machine was easier to operate than I expected.	This machine was easier to use than I expected.	比較	INTERMEDIATE
216	私は財布を忘れたことに駅で気付きました。	I realized I had forgotten my wallet at the station.	I noticed I had left my wallet behind at the station.	過去完了	INTERMEDIATE
217	彼女はその知らせを聞いて安心したようでした。	She seemed relieved after hearing the news.	She looked relieved after hearing the news.	分詞	INTERMEDIATE
218	私たちは渋滞を避けるために早めに出発しました。	We left early to avoid heavy traffic.	We started early to avoid heavy traffic.	不定詞	INTERMEDIATE
219	彼は普通ならそんな失敗はしません。	He would not normally make such a mistake.	He usually would not make such a mistake.	\N	INTERMEDIATE
220	私は彼にできるだけ早く返信するよう頼みました。	I asked him to reply as soon as possible.	I asked him to respond as soon as possible.	不定詞	INTERMEDIATE
221	その経験のおかげで私はもっと自信を持てるようになりました。	The experience made me more confident.	The experience helped me become more confident.	使役動詞	INTERMEDIATE
222	彼女は私が期待していたより落ち着いていました。	She was calmer than I had expected.	She was more relaxed than I had expected.	比較	INTERMEDIATE
223	この製品は多くの国で販売されています。	This product is sold in many countries.	This product is available in many countries.	受動態	INTERMEDIATE
224	もし私にもう少し時間があれば、その計画に参加するでしょう。	If I had a little more time, I would join the project.	If I had more time, I would take part in the project.	仮定法	INTERMEDIATE
225	私は最近、仕事と生活のバランスを見直し始めました。	I have recently begun to reconsider my work-life balance.	I have recently started to reconsider my work-life balance.	現在完了	INTERMEDIATE
226	その会社は新しいシステムを導入することを決めました。	The company decided to introduce a new system.	The company decided to adopt a new system.	\N	INTERMEDIATE
227	私は彼の提案を前向きに検討するつもりです。	I will seriously consider his proposal.	I am going to seriously consider his proposal.	\N	INTERMEDIATE
228	彼女はその仕事を予想以上に早く終えました。	She finished the task earlier than expected.	She completed the task earlier than expected.	比較	INTERMEDIATE
229	私は会議中に重要な点をメモしました。	I took notes on the important points during the meeting.	I wrote down the important points during the meeting.	\N	INTERMEDIATE
230	彼は普通の生活に戻るまで数週間かかりました。	It took him several weeks to return to his ordinary life.	He needed several weeks to return to his ordinary life.	無生物主語	INTERMEDIATE
231	私は海外で働く経験を一度はしてみたいです。	I would like to experience working abroad someday.	I want to experience working abroad someday.	動名詞	INTERMEDIATE
232	彼女は十分な情報が集まるまで判断を保留しました。	She delayed her decision until she had sufficient information.	She postponed her decision until she had enough information.	\N	INTERMEDIATE
233	私はその記事を読んで自分の考えを少し変えました。	Reading the article changed my opinion a little.	The article changed my opinion a little.	動名詞	INTERMEDIATE
234	彼は会議に遅れた理由を説明しました。	He explained why he was late for the meeting.	He explained the reason he was late for the meeting.	間接疑問文	INTERMEDIATE
235	私たちは悪天候にもかかわらず予定どおり出発しました。	We left as scheduled despite the bad weather.	We departed as scheduled despite the bad weather.	\N	INTERMEDIATE
236	私はその結果にかなり満足しています。	I am quite satisfied with the result.	I am fairly satisfied with the result.	\N	INTERMEDIATE
237	彼女はその計画を実現するために努力し続けました。	She kept working to achieve the plan.	She continued working to achieve the plan.	不定詞	INTERMEDIATE
238	私はその製品の品質に感心しました。	I was impressed by the quality of the product.	The quality of the product impressed me.	受動態	INTERMEDIATE
239	彼は新しい環境にすぐ適応しました。	He quickly adapted to the new environment.	He adjusted to the new environment quickly.	\N	INTERMEDIATE
240	私はその映画を見て結末に驚きました。	I was surprised by the ending after watching the movie.	The ending surprised me after I watched the movie.	受動態	INTERMEDIATE
241	彼女は私に冷静になるよう助言してくれました。	She advised me to stay calm.	She told me to stay calm.	不定詞	INTERMEDIATE
242	私は以前より効率よく仕事ができるようになりました。	I can work more efficiently than before.	I am able to work more efficiently than before.	比較	INTERMEDIATE
243	そのニュースは世界中にすぐ広まりました。	The news spread around the world quickly.	The news quickly spread throughout the world.	\N	INTERMEDIATE
245	彼女はその機会を逃さないようにしました。	She tried not to miss the opportunity.	She made sure not to miss the opportunity.	不定詞	INTERMEDIATE
246	私はその報告書を提出する前にもう一度確認しました。	I checked the report once more before submitting it.	I reviewed the report once more before submitting it.	動名詞	INTERMEDIATE
247	彼は期待どおり素晴らしい成果を上げました。	He achieved excellent results as expected.	He produced excellent results as expected.	\N	INTERMEDIATE
248	私は彼女が正しい判断をしたと思います。	I think she made the right decision.	I believe she made the correct decision.	\N	INTERMEDIATE
249	もしもっと準備していたら、自信を持って発表できたでしょう。	If I had prepared more, I could have given the presentation confidently.	If I had prepared better, I could have presented more confidently.	仮定法	INTERMEDIATE
250	私たちは全員、その提案に賛成しました。	All of us agreed with the proposal.	We all agreed with the proposal.	\N	INTERMEDIATE
251	私は重要な決断を下す前に家族へ相談しました。	I consulted my family before making an important decision.	I talked with my family before making an important decision.	動名詞	INTERMEDIATE
252	彼は予想外の質問にも落ち着いて答えました。	He answered the unexpected questions calmly.	He responded calmly to the unexpected questions.	\N	INTERMEDIATE
253	私は最近、健康的な食事を心がけています。	I have recently tried to maintain a healthy diet.	I have recently tried to eat more healthily.	現在完了	INTERMEDIATE
254	その結果は私たちの予想とは大きく異なっていました。	The result was quite different from what we had expected.	The outcome was quite different from what we had expected.	過去完了	INTERMEDIATE
255	彼女は新しい環境に慣れるのに苦労しました。	She had difficulty getting used to the new environment.	She struggled to get used to the new environment.	get used to	INTERMEDIATE
256	私はこのプロジェクトに参加できて光栄です。	I am honored to participate in this project.	I am honored to take part in this project.	不定詞	INTERMEDIATE
257	彼はその事故について詳しく説明しました。	He explained the accident in detail.	He described the accident in detail.	\N	INTERMEDIATE
258	私はその提案が現実的だと思います。	I think the proposal is realistic.	I believe the proposal is practical.	\N	INTERMEDIATE
259	このアプリのおかげで勉強が楽しくなりました。	This app has made studying more enjoyable.	This app has made learning more enjoyable.	現在完了	INTERMEDIATE
260	私たちは問題の原因を特定する必要があります。	We need to identify the cause of the problem.	We need to find the cause of the problem.	\N	INTERMEDIATE
261	彼女は重要な会議に出席する予定です。	She is going to attend an important meeting.	She is going to participate in an important meeting.	\N	INTERMEDIATE
262	私は英語で自分の意見を表現する練習をしています。	I practice expressing my opinions in English.	I practice expressing my ideas in English.	動名詞	INTERMEDIATE
263	その仕事は思ったほど複雑ではありませんでした。	The task was not as complicated as I had expected.	The task wasn't as difficult as I had expected.	比較	INTERMEDIATE
264	彼は十分な証拠を集めることができませんでした。	He could not collect sufficient evidence.	He couldn't gather enough evidence.	\N	INTERMEDIATE
265	私は彼女に時間を無駄にしないよう忠告しました。	I advised her not to waste her time.	I told her not to waste her time.	不定詞	INTERMEDIATE
266	その経験は私の考え方を大きく変えました。	The experience completely changed the way I think.	The experience greatly changed my way of thinking.	無生物主語	INTERMEDIATE
267	私たちは出発前に最終確認を行いました。	We made a final check before departure.	We did a final check before leaving.	\N	INTERMEDIATE
268	彼女は忙しいにもかかわらず私を手伝ってくれました。	She assisted me despite being very busy.	She helped me despite being very busy.	分詞構文	INTERMEDIATE
269	私はそのニュースを聞いて安心すると同時に驚きました。	I was relieved and surprised at the same time.	I felt both relieved and surprised.	\N	INTERMEDIATE
270	彼は以前より自信を持って英語を話しています。	He speaks English more confidently than before.	He speaks English with more confidence than before.	比較	INTERMEDIATE
271	もし私がその場にいたら、同じことをしたでしょう。	If I had been there, I would have done the same thing.	\N	仮定法	INTERMEDIATE
272	私は締め切りまでに報告書を完成させる必要があります。	I need to complete the report before the deadline.	I have to finish the report before the deadline.	\N	INTERMEDIATE
273	彼女はその問題を解決する方法を提案しました。	She suggested a way to solve the problem.	She suggested how to solve the problem.	不定詞	INTERMEDIATE
274	私は彼の成功は努力の結果だと思います。	I think his success is the result of hard work.	I believe his success comes from hard work.	\N	INTERMEDIATE
275	その計画は全員に承認されました。	The plan was approved by everyone.	Everyone approved the plan.	受動態	INTERMEDIATE
276	私は彼の意見を尊重していますが、今回は賛成できません。	I respect his opinion, but I cannot agree with him this time.	I respect his opinion, but I don't agree with him this time.	\N	INTERMEDIATE
277	彼女は仕事と家庭の両立に苦労しています。	She is struggling to balance her work and family life.	She is having difficulty balancing work and family life.	不定詞	INTERMEDIATE
278	私は会議の内容を簡単にまとめました。	I briefly summarized the meeting.	I gave a brief summary of the meeting.	\N	INTERMEDIATE
279	その変更は予想以上に大きな影響を与えました。	The change had a greater impact than we expected.	The change affected us more than we expected.	比較	INTERMEDIATE
280	彼はその提案を受け入れるか迷っています。	He is unsure whether to accept the proposal.	He cannot decide whether to accept the proposal.	間接疑問文	INTERMEDIATE
281	私は健康を改善するために運動を始めました。	I began exercising to improve my health.	I started exercising to improve my health.	不定詞	INTERMEDIATE
282	彼女は私にもっと自信を持つよう励ましてくれました。	She encouraged me to be more confident.	She encouraged me to have more confidence.	不定詞	INTERMEDIATE
283	その製品は期待していたほど売れませんでした。	The product did not sell as well as expected.	The product wasn't as successful as expected.	as...as	INTERMEDIATE
284	私は彼が約束を守ると信頼しています。	I trust him to keep his promise.	I believe he will keep his promise.	不定詞	INTERMEDIATE
285	私たちは全員、その決定に満足していました。	All of us were satisfied with the decision.	Everyone was satisfied with the decision.	\N	INTERMEDIATE
286	彼は海外勤務の経験があります。	He has experience working overseas.	He has experience of working overseas.	動名詞	INTERMEDIATE
287	私はこの方法が最も効率的だと思います。	I think this method is the most efficient.	I believe this method is the most effective.	比較	INTERMEDIATE
288	彼女は顧客からの問い合わせにすぐ対応しました。	She responded to the customer's inquiry immediately.	She answered the customer's question immediately.	\N	INTERMEDIATE
289	私はその記事を読んで多くのことを学びました。	I learned a lot from reading the article.	I learned many things by reading the article.	動名詞	INTERMEDIATE
290	その知らせで彼は安心した表情を見せました。	The news made him look relieved.	The news made him seem relieved.	使役動詞	INTERMEDIATE
291	私たちは全員、その結果に驚きました。	We were all surprised by the result.	All of us were surprised by the result.	受動態	INTERMEDIATE
292	彼は重要な情報を私たちと共有してくれました。	He shared important information with us.	He shared some important information with us.	\N	INTERMEDIATE
293	私は彼女にもう一度確認するようお願いしました。	I asked her to check it one more time.	I asked her to confirm it once again.	不定詞	INTERMEDIATE
332	彼はその契約に同意することを拒否しました。	He refused to agree to the contract.	He refused to accept the contract.	不定詞	INTERMEDIATE
294	その経験は私に忍耐の大切さを教えてくれました。	The experience taught me the importance of patience.	The experience showed me the importance of being patient.	無生物主語	INTERMEDIATE
295	私は予算内で新しいパソコンを購入できました。	I was able to purchase a new computer within my budget.	I managed to buy a new computer within my budget.	助動詞	INTERMEDIATE
296	彼女は以前より積極的に意見を言うようになりました。	She expresses her opinions more actively than before.	She speaks up more actively than before.	比較	INTERMEDIATE
297	もしもっと慎重だったら、その間違いは防げたでしょう。	If I had been more careful, I could have avoided the mistake.	\N	仮定法	INTERMEDIATE
298	私は締め切りまでに資料を準備するつもりです。	I am going to prepare the materials before the deadline.	I will prepare the materials before the deadline.	\N	INTERMEDIATE
299	彼はその状況を冷静に判断しました。	He judged the situation calmly.	He assessed the situation calmly.	\N	INTERMEDIATE
300	この経験は将来きっと役に立つでしょう。	This experience will certainly be useful in the future.	This experience will definitely help me in the future.	\N	INTERMEDIATE
301	私はその問題について上司と相談する必要があります。	I need to discuss the issue with my manager.	I need to talk about the issue with my manager.	\N	INTERMEDIATE
302	彼女は新しい仕事にすぐ慣れました。	She quickly adapted to her new job.	She got used to her new job quickly.	\N	INTERMEDIATE
303	私はできるだけ早く返事をするつもりです。	I intend to reply as soon as possible.	I plan to reply as soon as possible.	\N	INTERMEDIATE
304	その映画は私が期待していたより感動的でした。	The movie was more moving than I had expected.	The movie was more touching than I had expected.	比較	INTERMEDIATE
305	彼は会議の開始時間を勘違いしていました。	He misunderstood the meeting's starting time.	He got the meeting time wrong.	\N	INTERMEDIATE
306	私は彼女に計画を変更するよう提案しました。	I suggested that she change the plan.	I suggested changing the plan to her.	動名詞	INTERMEDIATE
307	このソフトは操作方法を覚えるのが簡単です。	This software is easy to learn how to use.	This software is easy to use.	不定詞	INTERMEDIATE
308	彼は以前より責任感が強くなりました。	He has become more responsible than before.	He is more responsible than he used to be.	現在完了	INTERMEDIATE
309	私はその記事を最後まで読みませんでした。	I did not read the article to the end.	I didn't finish reading the article.	動名詞	INTERMEDIATE
310	彼女は顧客の要望に柔軟に対応しました。	She responded to the customer's request flexibly.	She dealt with the customer's request flexibly.	\N	INTERMEDIATE
311	私はその提案に完全に賛成というわけではありません。	I do not completely agree with the proposal.	I don't fully agree with the proposal.	\N	INTERMEDIATE
312	この橋は去年改修されました。	This bridge was renovated last year.	This bridge was repaired last year.	受動態	INTERMEDIATE
313	彼は英語でプレゼンすることに慣れています。	He is used to giving presentations in English.	He is accustomed to giving presentations in English.	get used to	INTERMEDIATE
314	私は彼の説明のおかげで内容を理解できました。	His explanation helped me understand the content.	Thanks to his explanation, I understood the content.	使役動詞	INTERMEDIATE
315	その知らせで私たちは計画を変更しました。	The news made us change our plans.	Because of the news, we changed our plans.	使役動詞	INTERMEDIATE
316	彼女は誰よりも冷静にその状況へ対応しました。	She handled the situation more calmly than anyone else.	She dealt with the situation more calmly than anyone else.	比較	INTERMEDIATE
317	私は海外旅行を計画するのが好きです。	I enjoy planning trips abroad.	I like planning overseas trips.	動名詞	INTERMEDIATE
318	その問題は思ったより早く解決しました。	The problem was solved sooner than we expected.	The issue was resolved sooner than we expected.	受動態	INTERMEDIATE
319	私は彼にもっと具体的に説明してほしいです。	I would like him to explain it more specifically.	I want him to explain it in more detail.	不定詞	INTERMEDIATE
320	もし私がその情報を知っていたら、違う判断をしたでしょう。	If I had known the information, I would have made a different decision.	\N	仮定法	INTERMEDIATE
321	彼は仕事を終えてからジムへ向かいました。	He went to the gym after finishing his work.	He headed to the gym after finishing work.	動名詞	INTERMEDIATE
322	私は重要なファイルを誤って上書きしてしまいました。	I accidentally overwrote an important file.	I accidentally replaced an important file.	\N	INTERMEDIATE
323	彼女は予算内で旅行を楽しむ方法を知っています。	She knows how to enjoy traveling within a budget.	She knows how to travel on a budget.	疑問詞+不定詞	INTERMEDIATE
324	その経験は私に挑戦する勇気を与えてくれました。	The experience gave me the courage to take on new challenges.	The experience gave me confidence to face new challenges.	\N	INTERMEDIATE
325	私は彼の努力は必ず報われると思います。	I believe his efforts will eventually pay off.	I think his hard work will eventually pay off.	\N	INTERMEDIATE
326	私たちは予定を変更せざるを得ませんでした。	We had to change our schedule because of the weather.	We were forced to change our schedule because of the weather.	\N	INTERMEDIATE
327	私はこの方法の方がずっと効率的だと思います。	I think this method is much more efficient.	I believe this method is far more efficient.	比較	INTERMEDIATE
328	彼は会議の内容を全員に共有しました。	He shared the meeting details with everyone.	He shared what was discussed in the meeting with everyone.	\N	INTERMEDIATE
329	私は締め切りまでにこの仕事を終えられると思います。	I think I can finish this task before the deadline.	I believe I can complete this task before the deadline.	\N	INTERMEDIATE
330	彼女は私の質問に丁寧に答えてくれました。	She answered my question politely.	She responded to my question politely.	\N	INTERMEDIATE
331	私は英語を話すたびに自信がついてきます。	I become more confident every time I speak English.	I gain more confidence every time I speak English.	比較	INTERMEDIATE
333	このデータは慎重に扱う必要があります。	This data should be handled carefully.	This information should be handled carefully.	受動態	INTERMEDIATE
334	私は彼女にもう一度確認するよう伝えました。	I told her to check it again.	I asked her to check it once more.	不定詞	INTERMEDIATE
335	彼は予想していたより早く回復しました。	He recovered sooner than we had expected.	He recovered faster than we had expected.	比較	INTERMEDIATE
336	私はその商品を返品することにしました。	I decided to return the product.	I decided to send the product back.	不定詞	INTERMEDIATE
337	彼女はその問題を一人で解決しました。	She solved the problem on her own.	She solved the problem by herself.	\N	INTERMEDIATE
338	私は重要な情報を見落としていました。	I had overlooked important information.	I had missed some important information.	過去完了	INTERMEDIATE
339	そのニュースは私たちに大きな希望を与えました。	The news gave us great hope.	The news gave all of us great hope.	\N	INTERMEDIATE
340	彼は海外勤務に興味を持っています。	He is interested in working overseas.	He is interested in working abroad.	動名詞	INTERMEDIATE
341	私は彼らの提案を受け入れることにしました。	I decided to accept their proposal.	I decided to accept their suggestion.	不定詞	INTERMEDIATE
342	その会社は新しい支店を大阪に開設しました。	The company opened a new branch in Osaka.	The company established a new branch in Osaka.	\N	INTERMEDIATE
343	彼女はその説明を聞いて安心したようでした。	She seemed relieved after hearing the explanation.	She looked relieved after hearing the explanation.	分詞	INTERMEDIATE
344	私はその決定を少し後悔しています。	I somewhat regret making that decision.	I slightly regret making that decision.	動名詞	INTERMEDIATE
345	もし私がもっと経験豊富だったら、その仕事を引き受けたでしょう。	If I had been more experienced, I would have accepted the job.	\N	仮定法	INTERMEDIATE
346	彼は英語だけでなく中国語も流暢に話します。	He speaks not only English but also Chinese fluently.	He can speak both English and Chinese fluently.	\N	INTERMEDIATE
347	私は彼女の説明で誤解が解けました。	Her explanation cleared up my misunderstanding.	Her explanation helped me understand the situation.	無生物主語	INTERMEDIATE
348	その資料は昨日全員に配布されました。	The documents were distributed to everyone yesterday.	The materials were handed out to everyone yesterday.	受動態	INTERMEDIATE
349	私はこの仕事を一人で終わらせる自信があります。	I am confident that I can finish this task alone.	I am confident I can complete this task by myself.	\N	INTERMEDIATE
350	彼は新しい環境でもすぐに実力を発揮しました。	He quickly demonstrated his ability in the new environment.	He quickly showed his ability in the new environment.	\N	INTERMEDIATE
351	私はその問題を別の視点から考えてみました。	I tried to look at the problem from a different perspective.	I tried to see the problem from a different point of view.	\N	INTERMEDIATE
352	彼は約束どおり時間通りに到着しました。	He arrived on time as he had promised.	He arrived as promised.	過去完了	INTERMEDIATE
353	私は彼女の提案にはある程度賛成しています。	I agree with her proposal to some extent.	I agree with her suggestion to some degree.	\N	INTERMEDIATE
354	そのアプリのおかげで作業時間を短縮できました。	The app helped me reduce my working time.	The app helped me save time at work.	使役動詞	INTERMEDIATE
355	私はその件について詳しく説明するつもりです。	I am going to explain the matter in detail.	I will explain the matter in detail.	\N	INTERMEDIATE
356	彼は重要な会議を欠席しました。	He was absent from the important meeting.	He missed the important meeting.	\N	INTERMEDIATE
357	私は以前より人前で話すことに慣れてきました。	I have become more comfortable speaking in public.	I have gotten more comfortable speaking in public.	現在完了	INTERMEDIATE
358	その結果は私たちを少しがっかりさせました。	The result disappointed us a little.	The result made us a little disappointed.	無生物主語	INTERMEDIATE
359	私は彼にできるだけ早く連絡するよう伝えました。	I told him to contact me as soon as possible.	I asked him to contact me as soon as possible.	不定詞	INTERMEDIATE
360	彼女は会議中ずっとメモを取っていました。	She kept taking notes throughout the meeting.	She continued taking notes throughout the meeting.	動名詞	INTERMEDIATE
361	私はこのプロジェクトから多くのことを学びました。	I learned a great deal from this project.	I learned a lot from this project.	\N	INTERMEDIATE
362	彼は冷静に状況を分析しました。	He analyzed the situation calmly.	He examined the situation calmly.	\N	INTERMEDIATE
363	私は彼女に謝るべきだったと思います。	I think I should have apologized to her.	I believe I should have apologized to her.	助動詞	INTERMEDIATE
364	その製品は予想以上によく売れました。	The product sold better than expected.	The product sold much better than we expected.	比較	INTERMEDIATE
365	私たちは計画を見直す必要があります。	We need to review the plan.	We need to reconsider the plan.	\N	INTERMEDIATE
366	彼女は外国人のお客様に対応しています。	She is assisting customers from overseas.	She is helping customers from overseas.	\N	INTERMEDIATE
367	私はその会議に参加できなかったことを残念に思います。	I regret not being able to attend the meeting.	I am sorry that I could not attend the meeting.	動名詞	INTERMEDIATE
368	彼は十分な準備をして試験に臨みました。	He took the exam with sufficient preparation.	He took the exam after preparing well.	\N	INTERMEDIATE
369	私は彼らが合意に達したと聞きました。	I heard that they reached an agreement.	I heard they came to an agreement.	\N	INTERMEDIATE
370	その経験は私の考え方に大きな影響を与えました。	The experience had a significant impact on my thinking.	The experience greatly influenced my way of thinking.	\N	INTERMEDIATE
371	もし私があなたの立場だったら、同じ決断をしたでしょう。	If I had been in your position, I would have made the same decision.	\N	仮定法	INTERMEDIATE
372	私はその本を読み終えるまで眠りませんでした。	I did not go to bed until I finished reading the book.	I stayed awake until I finished reading the book.	動名詞	INTERMEDIATE
373	彼女はその説明を聞いて安心した表情を見せました。	She looked relieved after listening to the explanation.	She seemed relieved after hearing the explanation.	分詞	INTERMEDIATE
374	その会社は来年新しいサービスを開始する予定です。	The company plans to launch a new service next year.	The company plans to introduce a new service next year.	\N	INTERMEDIATE
375	私は将来海外で働く可能性を考えています。	I am considering the possibility of working abroad someday.	I am thinking about working abroad in the future.	動名詞	INTERMEDIATE
376	私たちは予算を超えないように計画を立てました。	We made a plan so that we would stay within our budget.	We planned everything to stay within our budget.	\N	INTERMEDIATE
377	彼は自分の意見をはっきり述べることをためらいませんでした。	He did not hesitate to express his opinion clearly.	He was not afraid to express his opinion clearly.	不定詞	INTERMEDIATE
378	私は彼女がその仕事に最も適した人だと思います。	I think she is the most suitable person for the job.	I believe she is the best person for the job.	比較	INTERMEDIATE
379	その変更によって作業がかなり効率的になりました。	The change made our work much more efficient.	The change improved our efficiency a lot.	使役動詞	INTERMEDIATE
380	私はその提案の利点をすぐ理解できました。	I quickly understood the advantages of the proposal.	I quickly understood the benefits of the proposal.	\N	INTERMEDIATE
381	彼は締め切りを守ることを常に心がけています。	He always tries to meet deadlines.	He always makes an effort to meet deadlines.	\N	INTERMEDIATE
382	私は以前より自分の考えを伝えやすくなりました。	I can express my thoughts more easily than before.	I can explain my ideas more easily than before.	比較	INTERMEDIATE
383	その製品は品質の高さで知られています。	The product is known for its high quality.	The product is famous for its high quality.	受動態	INTERMEDIATE
384	彼女は問題の原因を正確に説明しました。	She explained the cause of the problem accurately.	She accurately explained the cause of the problem.	\N	INTERMEDIATE
385	私は彼に最後まであきらめないよう励ましました。	I encouraged him not to give up until the end.	I encouraged him to keep trying until the end.	不定詞	INTERMEDIATE
386	その記事を読んで私は考え方が変わりました。	Reading the article changed the way I think.	The article changed the way I think.	動名詞	INTERMEDIATE
387	彼は新しい技術をすぐに習得しました。	He quickly mastered the new technology.	He quickly learned the new technology.	\N	INTERMEDIATE
388	私はその決定に完全には納得していません。	I am not completely satisfied with the decision.	I am not fully satisfied with the decision.	\N	INTERMEDIATE
389	彼女は重要な情報を見逃しませんでした。	She did not overlook the important information.	She did not miss the important information.	\N	INTERMEDIATE
390	私たちは問題が起こる前に対策を講じました。	We took action before the problem occurred.	We took measures before the problem occurred.	\N	INTERMEDIATE
391	私は英語で自然に話せるようになりたいです。	I want to be able to speak English naturally.	I would like to speak English more naturally.	不定詞	INTERMEDIATE
392	彼は誰よりも早く問題の原因を見つけました。	He found the cause of the problem faster than anyone else.	He identified the cause of the problem faster than anyone else.	比較	INTERMEDIATE
393	その経験によって私はもっと慎重になりました。	The experience made me more careful.	The experience helped me become more careful.	使役動詞	INTERMEDIATE
394	私はその計画を実現するのは難しいと思います。	I think it will be difficult to achieve the plan.	I think it will be difficult to carry out the plan.	\N	INTERMEDIATE
395	彼女は会議で自分の考えを自信を持って話しました。	She shared her ideas confidently at the meeting.	She expressed her opinions confidently at the meeting.	\N	INTERMEDIATE
396	もし私がもっと早く出発していたら、電車に間に合ったでしょう。	If I had left earlier, I would have caught the train.	\N	仮定法	INTERMEDIATE
397	私はその問題を解決する別の方法を提案しました。	I suggested another way to solve the problem.	I proposed another way to solve the problem.	不定詞	INTERMEDIATE
398	彼は顧客から高い評価を受けています。	He is highly regarded by his customers.	He is highly appreciated by his customers.	受動態	INTERMEDIATE
399	私たちは新しい目標を設定することにしました。	We decided to set a new goal.	We decided to establish a new goal.	不定詞	INTERMEDIATE
400	私はその失敗から大切な教訓を学びました。	I learned an important lesson from the failure.	I gained an important lesson from the failure.	\N	INTERMEDIATE
500	その仕事には高い集中力が必要です。	The task requires a high level of concentration.	The task needs a high level of concentration.	\N	INTERMEDIATE
501	私はその提案を慎重に検討しました。	I carefully considered the proposal.	I carefully thought about the proposal.	\N	INTERMEDIATE
502	彼は新しい職場にすぐ慣れました。	He quickly adjusted to his new workplace.	He quickly got used to his new workplace.	\N	INTERMEDIATE
503	その商品は予想以上によく売れました。	The product performed better than expected.	The product sold better than expected.	比較	INTERMEDIATE
504	私たちは問題の原因を特定しました。	We identified the cause of the problem.	We found the cause of the problem.	\N	INTERMEDIATE
505	私は会議の要点を簡潔に説明しました。	I briefly explained the main points of the meeting.	I briefly explained the key points of the meeting.	\N	INTERMEDIATE
506	彼女は重要な役割を果たしました。	She played an important role in the project.	She had an important role in the project.	\N	INTERMEDIATE
507	私たちは計画を少し修正する必要があります。	We need to modify the plan slightly.	We need to change the plan slightly.	\N	INTERMEDIATE
508	私は彼の意見を尊重しています。	I respect his opinion.	I value his opinion.	\N	INTERMEDIATE
509	その決定は合理的だったと思います。	I think the decision was reasonable.	I believe the decision was reasonable.	\N	INTERMEDIATE
510	彼女は期待以上の成果を出しました。	She achieved better results than expected.	She produced better results than expected.	比較	INTERMEDIATE
511	私はその問題を別の方法で解決しました。	I solved the problem in a different way.	I resolved the problem in a different way.	\N	INTERMEDIATE
512	彼はその状況を正確に説明しました。	He described the situation accurately.	He explained the situation accurately.	\N	INTERMEDIATE
513	私たちは長期的な目標を設定しました。	We set a long-term goal.	We established a long-term goal.	\N	INTERMEDIATE
514	私はその変化に少し驚きました。	I was somewhat surprised by the change.	I was a little surprised by the change.	\N	INTERMEDIATE
515	彼はすぐに自分の間違いを認めました。	He immediately admitted his mistake.	He admitted his mistake right away.	\N	INTERMEDIATE
516	私たちは全員その結果に満足しています。	All of us are satisfied with the result.	We are all satisfied with the result.	\N	INTERMEDIATE
517	彼女は問題を冷静に解決しました。	She solved the problem calmly.	She dealt with the problem calmly.	\N	INTERMEDIATE
518	私はそのニュースを信じられませんでした。	I could hardly believe the news.	I could not believe the news.	\N	INTERMEDIATE
519	彼は新しい仕事に大きな期待を寄せています。	He has high expectations for his new job.	He expects a lot from his new job.	\N	INTERMEDIATE
520	私はその仕事を予定より早く終えました。	I finished the task ahead of schedule.	I completed the task ahead of schedule.	\N	INTERMEDIATE
521	その計画は全員に受け入れられました。	The plan was accepted by everyone.	Everyone accepted the plan.	受動態	INTERMEDIATE
522	彼は重要な書類を提出しました。	He submitted the important documents.	He handed in the important documents.	\N	INTERMEDIATE
523	私たちは問題を解決するため協力しました。	We cooperated to solve the problem.	We worked together to solve the problem.	不定詞	INTERMEDIATE
524	私は以前より自信を持って話せます。	I can speak more confidently than before.	I speak with more confidence than before.	比較	INTERMEDIATE
525	私は彼の提案に前向きな印象を持ちました。	I had a positive impression of his proposal.	I got a positive impression of his proposal.	\N	INTERMEDIATE
526	その会議は予定どおり終了しました。	The meeting ended as scheduled.	The meeting finished as scheduled.	\N	INTERMEDIATE
527	彼女は問題の原因をすぐ理解しました。	She quickly understood the cause of the problem.	She quickly realized the cause of the problem.	\N	INTERMEDIATE
528	私たちはその計画を全面的に支持しました。	We fully supported the plan.	We completely supported the plan.	\N	INTERMEDIATE
529	私は仕事の優先順位を決めました。	I decided on the priorities for my work.	I set the priorities for my work.	\N	INTERMEDIATE
530	彼は予想以上に落ち着いていました。	He was calmer than I expected.	He was more relaxed than I expected.	比較	INTERMEDIATE
531	その資料は非常に役立ちました。	The material was very useful.	The material was extremely helpful.	\N	INTERMEDIATE
532	私は昨日の出来事を詳しく説明しました。	I explained yesterday's events in detail.	I described yesterday's events in detail.	\N	INTERMEDIATE
533	私たちは新しい方法を試してみました。	We tried a new approach.	We tried a new method.	\N	INTERMEDIATE
534	彼女は仕事に対して責任感があります。	She has a strong sense of responsibility for her work.	She is very responsible for her work.	\N	INTERMEDIATE
535	私は彼の判断を信頼しています。	I trust his judgment.	I have confidence in his judgment.	\N	INTERMEDIATE
536	その結果は私たちを安心させました。	The result made us feel relieved.	The result relieved us.	使役動詞	INTERMEDIATE
537	私はその話を最後まで聞きました。	I listened to the whole story.	I listened to the story until the end.	\N	INTERMEDIATE
538	彼はその仕事を快く引き受けました。	He gladly accepted the task.	He willingly accepted the task.	\N	INTERMEDIATE
539	私は十分な時間を確保できました。	I was able to secure enough time.	I managed to get enough time.	\N	INTERMEDIATE
540	私たちはその問題を真剣に議論しました。	We discussed the issue seriously.	We talked about the issue seriously.	\N	INTERMEDIATE
541	彼女はその経験から多くを学びました。	She learned a lot from the experience.	She gained a lot from the experience.	\N	INTERMEDIATE
542	私はその変更にすぐ気付きました。	I noticed the change immediately.	I realized the change immediately.	\N	INTERMEDIATE
543	その計画は順調に進んでいます。	The plan is progressing smoothly.	The plan is going smoothly.	現在進行形	INTERMEDIATE
544	彼は困難な状況でも前向きでした。	He stayed positive even in a difficult situation.	He remained positive even in a difficult situation.	\N	INTERMEDIATE
545	私はその説明に十分満足しています。	I am fully satisfied with the explanation.	I am completely satisfied with the explanation.	\N	INTERMEDIATE
546	彼女は仕事を効率的に進めました。	She completed the work efficiently.	She finished the work efficiently.	\N	INTERMEDIATE
547	私たちは予定を少し変更しました。	We made a slight change to the schedule.	We changed the schedule slightly.	\N	INTERMEDIATE
548	私はその決定に後悔はありません。	I do not regret the decision.	I don't regret my decision.	\N	INTERMEDIATE
549	彼は最後まで希望を失いませんでした。	He never lost hope until the end.	He did not lose hope until the end.	\N	INTERMEDIATE
421	彼は遅れを取り戻そうと努力しました。	He tried to make up for the delay.	He tried to recover the lost time.	\N	ADVANCED
422	私たちはその問題を後回しにしました。	We decided to put aside the issue for now.	We decided to postpone the issue for now.	不定詞	ADVANCED
423	私は結果を慎重に評価する必要があります。	I need to evaluate the results carefully.	I need to assess the results carefully.	\N	ADVANCED
660	その情報は機密扱いです。	The information is confidential.	The information is secret.	\N	ADVANCED
424	彼女は地域活動に積極的に貢献しています。	She contributes actively to the local community.	She actively contributes to the local community.	\N	ADVANCED
425	彼は急な予定変更にも柔軟に対応しました。	He responded flexibly to the sudden schedule change.	He reacted flexibly to the sudden schedule change.	\N	ADVANCED
426	私はその結論を簡単には受け入れられません。	I cannot readily accept that conclusion.	I cannot easily accept that conclusion.	\N	ADVANCED
427	その報告書はかなり説得力がありました。	The report was highly convincing.	The report was very persuasive.	\N	ADVANCED
428	彼女は私の提案を快く受け入れてくれました。	She willingly accepted my proposal.	She gladly accepted my proposal.	\N	ADVANCED
429	その薬は症状を徐々に和らげました。	The medicine gradually relieved the symptoms.	The medicine gradually eased the symptoms.	\N	ADVANCED
430	私たちは予算を適切に配分する必要があります。	We need to allocate the budget properly.	We need to distribute the budget properly.	\N	ADVANCED
431	私はその情報源の信頼性を疑っています。	I doubt the reliability of the source.	I question the reliability of the source.	\N	ADVANCED
432	その仕事にはかなりの忍耐が必要です。	The task requires considerable patience.	The task requires a great deal of patience.	\N	ADVANCED
433	彼は会議で重要な点を強調しました。	He emphasized the key point during the meeting.	He stressed the key point during the meeting.	\N	ADVANCED
434	私たちはその計画を慎重に修正しました。	We carefully revised the plan.	We carefully modified the plan.	\N	ADVANCED
435	彼女はその変化にすぐ適応しました。	She adapted to the change quickly.	She adjusted to the change quickly.	\N	ADVANCED
436	私はその決定を支持する十分な理由があります。	I have sufficient reasons to support the decision.	I have enough reasons to support the decision.	\N	ADVANCED
437	彼は私の期待を大きく上回りました。	He far exceeded my expectations.	He went far beyond my expectations.	\N	ADVANCED
438	私たちはその提案を全会一致で承認しました。	We approved the proposal unanimously.	We accepted the proposal unanimously.	\N	ADVANCED
439	その政策は長期的な利益をもたらすでしょう。	The policy will provide long-term benefits.	The policy will bring long-term benefits.	\N	ADVANCED
440	私は彼の発言を完全に誤解していました。	I had completely misunderstood his remark.	I had completely misunderstood what he said.	過去完了	ADVANCED
441	その数字は昨年より大幅に増加しました。	The figure increased dramatically from last year.	The figure rose dramatically from last year.	\N	ADVANCED
442	彼は期待以上の成果を達成しました。	He achieved outstanding results.	He achieved excellent results.	\N	ADVANCED
443	私たちは古い規則を廃止することにしました。	We decided to abolish the old rule.	We decided to remove the old rule.	不定詞	ADVANCED
444	その事故は深刻な損害を引き起こしました。	The accident caused severe damage.	The accident caused serious damage.	\N	ADVANCED
445	私は彼女の成功を心から尊敬しています。	I genuinely admire her success.	I truly admire her success.	\N	ADVANCED
446	その仕事は予想以上に複雑でした。	The task turned out to be complicated.	The task proved to be complicated.	\N	ADVANCED
447	彼は限られた資源を有効に活用しました。	He utilized the limited resources effectively.	He used the limited resources effectively.	\N	ADVANCED
448	私たちはその方針を徐々に受け入れました。	We gradually accepted the policy.	We gradually came to accept the policy.	\N	ADVANCED
449	彼女は新しい環境でもすぐ能力を発揮しました。	She demonstrated her ability in the new environment.	She showed her ability in the new environment.	\N	ADVANCED
450	彼はその契約の内容を慎重に確認しました。	He carefully reviewed the contract before signing it.	He carefully examined the contract before signing it.	\N	ADVANCED
451	私はその仕事を自発的に引き受けました。	I volunteered to take the task.	I offered to take the task.	不定詞	ADVANCED
452	その計画は現実的ではありません。	The plan is not practical.	The plan is unrealistic.	\N	ADVANCED
453	彼女は困難な状況でも冷静さを保ちました。	She remained calm under difficult circumstances.	She stayed calm under difficult circumstances.	\N	ADVANCED
454	私たちはその変更を正式に発表しました。	We officially announced the change.	We made the change official.	\N	ADVANCED
455	彼は重要な責任を任されました。	He was assigned an important responsibility.	He was given an important responsibility.	受動態	ADVANCED
456	私はその提案に反対する理由がありません。	I have no reason to oppose the proposal.	I have no reason to object to the proposal.	\N	ADVANCED
457	その結果は私の予想と一致していました。	The result was consistent with my expectation.	The result matched my expectation.	\N	ADVANCED
458	彼女は新しい方法を積極的に採用しました。	She readily adopted the new approach.	She willingly adopted the new approach.	\N	ADVANCED
459	私たちは全体的な状況を見直しました。	We reviewed the overall situation.	We examined the overall situation.	\N	ADVANCED
460	彼は十分な証拠を提示しました。	He presented sufficient evidence.	He provided sufficient evidence.	\N	ADVANCED
461	私は彼の説明が納得できると思いました。	I found his explanation convincing.	His explanation sounded convincing to me.	\N	ADVANCED
462	その仕事はかなり負担になりました。	The task placed a considerable burden on us.	The task became a heavy burden for us.	\N	ADVANCED
463	彼女は問題の本質をすぐ理解しました。	She quickly recognized the core issue.	She quickly understood the main issue.	\N	ADVANCED
471	彼は問題を客観的に分析しました。	He analyzed the problem objectively.	He examined the problem objectively.	\N	ADVANCED
472	私たちは長期的な視点で判断しました。	We made the decision from a long-term perspective.	We made the decision with a long-term perspective.	\N	ADVANCED
473	彼女はその提案に前向きな姿勢を示しました。	She showed a positive attitude toward the proposal.	She had a positive attitude toward the proposal.	\N	ADVANCED
474	私はその決断を後悔していません。	I do not regret making the decision.	I don't regret making that decision.	動名詞	ADVANCED
475	私は彼の説明があいまいだと感じました。	I found his explanation rather vague.	I thought his explanation was rather vague.	\N	ADVANCED
476	その会社は市場で優位性を維持しています。	The company maintains an advantage in the market.	The company keeps an advantage in the market.	\N	ADVANCED
477	彼女は私の意見に賛同してくれました。	She agreed with my opinion willingly.	She supported my opinion willingly.	\N	ADVANCED
478	その計画は現時点では実現不可能です。	The plan is currently impractical.	The plan is not practical at the moment.	\N	ADVANCED
479	彼は予想外の質問にも冷静に対応しました。	He handled the unexpected question professionally.	He dealt with the unexpected question professionally.	\N	ADVANCED
480	私たちは最終案を慎重に比較しました。	We carefully compared the final proposals.	We carefully compared the final plans.	\N	ADVANCED
481	その判断は十分に正当化できます。	The decision is fully justified.	The decision can be fully justified.	受動態	ADVANCED
482	私はその説明に完全には納得していません。	I am not entirely convinced by the explanation.	I am not completely convinced by the explanation.	\N	ADVANCED
483	彼は自分の能力を過小評価しています。	He underestimates his own ability.	He thinks too little of his own ability.	\N	ADVANCED
484	私たちは新しい戦略を採用しました。	We adopted a new strategy.	We introduced a new strategy.	\N	ADVANCED
485	彼女は重要な点を明確にしました。	She clarified the important point.	She made the important point clear.	\N	ADVANCED
486	私はその考え方に強く共感します。	I strongly relate to that idea.	I strongly identify with that idea.	\N	ADVANCED
487	彼は私の提案を即座に却下しました。	He immediately rejected my proposal.	He immediately turned down my proposal.	\N	ADVANCED
488	その方法は予想より効果的でした。	The approach proved more effective than expected.	The approach turned out to be more effective than expected.	比較	ADVANCED
489	彼女は自分の責任を十分理解しています。	She is fully aware of her responsibility.	She completely understands her responsibility.	\N	ADVANCED
490	私は会議の内容を要約しました。	I summarized the discussion after the meeting.	I summarized what we discussed after the meeting.	\N	ADVANCED
491	彼は短期間で大きく成長しました。	He made remarkable progress in a short time.	He improved remarkably in a short time.	\N	ADVANCED
492	その報告書には重要な情報が含まれています。	The report contains essential information.	The report includes essential information.	\N	ADVANCED
493	私は彼の努力を心から評価しています。	I sincerely appreciate his efforts.	I truly appreciate his efforts.	\N	ADVANCED
494	その変化は徐々に受け入れられました。	The change was gradually accepted.	People gradually accepted the change.	受動態	ADVANCED
495	彼女は慎重に言葉を選びました。	She chose her words carefully.	She selected her words carefully.	\N	ADVANCED
496	その契約は双方に利益をもたらします。	The agreement benefits both parties.	The agreement is beneficial to both parties.	\N	ADVANCED
497	私は状況に応じて計画を調整しました。	I adjusted the plan according to the situation.	I modified the plan according to the situation.	\N	ADVANCED
498	彼は重要な責任を引き受けました。	He assumed an important responsibility.	He took on an important responsibility.	\N	ADVANCED
499	私たちはその提案を慎重に検討する必要があります。	We need to examine the proposal carefully.	We need to consider the proposal carefully.	\N	ADVANCED
552	彼は彼女の本当の気持ちを見抜きました。	He discerned her true feelings.	He recognized her true feelings.	\N	ADVANCED
553	彼は成功するために懸命に努力しました。	He endeavored to succeed despite many difficulties.	He made every effort to succeed despite many difficulties.	不定詞	ADVANCED
554	彼女は報告書を非常に丁寧に確認しました。	She examined the report in a meticulous manner.	She examined the report very carefully.	\N	ADVANCED
555	その説明はもっともらしく聞こえました。	The explanation sounded plausible to everyone.	The explanation seemed believable to everyone.	\N	ADVANCED
556	最近、彼の健康状態は悪化しています。	His health has gradually deteriorated recently.	His health has gradually become worse recently.	現在完了	ADVANCED
557	私たちは予算を各部署へ割り当てました。	We allocated the budget to each department.	We distributed the budget to each department.	\N	ADVANCED
558	彼は会議で新しい計画をまとめました。	He formulated a new plan during the meeting.	He created a new plan during the meeting.	\N	ADVANCED
559	その状況のため私は参加せざるを得ませんでした。	The situation compelled me to join the meeting.	The situation forced me to join the meeting.	\N	ADVANCED
560	私は後でそのファイルを取り出しました。	I retrieved the file later that afternoon.	I got the file back later that afternoon.	\N	ADVANCED
561	彼女は自分の考えをはっきり伝えました。	She conveyed her opinion clearly.	She expressed her opinion clearly.	\N	ADVANCED
562	彼は古い伝統を守ろうとしました。	He tried to retain the old tradition.	He tried to preserve the old tradition.	不定詞	ADVANCED
563	この機械はもう時代遅れです。	This machine has become obsolete.	This machine is no longer useful.	現在完了	ADVANCED
564	結果は避けられないようでした。	The result seemed inevitable.	The result seemed impossible to avoid.	\N	ADVANCED
565	彼女は契約書を細かく調べました。	She scrutinized the contract carefully.	She examined the contract carefully.	\N	ADVANCED
566	私は彼の表情から答えを推測しました。	I inferred the answer from his expression.	I guessed the answer from his expression.	\N	ADVANCED
567	その計画は十分実行可能です。	The proposal is entirely viable.	The proposal is workable.	\N	ADVANCED
568	私は彼の意見を全面的に支持します。	I fully endorse his opinion.	I fully support his opinion.	\N	ADVANCED
569	彼の説明はとても筋が通っていました。	His explanation was completely coherent.	His explanation was very logical.	\N	ADVANCED
570	その仕組みは思ったより複雑でした。	The system was surprisingly intricate.	The system was surprisingly complicated.	\N	ADVANCED
571	彼女は困難な状況でも立ち直りました。	She remained resilient after the difficult experience.	She stayed strong after the difficult experience.	\N	ADVANCED
572	英語力はその仕事の必須条件です。	English ability is a prerequisite for the job.	English ability is required for the job.	\N	ADVANCED
573	彼は最後には両者の意見を一致させました。	He finally reconciled the two opinions.	He finally brought the two opinions together.	\N	ADVANCED
574	私たちは手続きを早める必要があります。	We need to expedite the process.	We need to speed up the process.	不定詞	ADVANCED
575	その計画は予算内でも実行できます。	The project is feasible within our budget.	The project can be done within our budget.	\N	ADVANCED
576	私はその計画を修正する必要があります。	I need to revise the plan before tomorrow.	I need to modify the plan before tomorrow.	\N	ADVANCED
577	彼は会社の規則を守ることに同意しました。	He agreed to comply with the company rules.	He agreed to follow the company rules.	不定詞	ADVANCED
578	この新しい機能は作業を楽にしてくれます。	This feature will facilitate our work.	This feature will make our work easier.	\N	ADVANCED
579	私は必要な情報を一つの資料にまとめました。	I compiled the information into one document.	I gathered the information into one document.	\N	ADVANCED
580	彼女はその仕事を部下に任せました。	She delegated the task to her assistant.	She assigned the task to her assistant.	\N	ADVANCED
581	私たちは業務をもっと効率化したいです。	We want to optimize our workflow.	We want to improve our workflow.	不定詞	ADVANCED
582	その変更で手続きが簡単になりました。	The change streamlined the process.	The change simplified the process.	\N	ADVANCED
583	彼は二つの部署を一つにまとめました。	He consolidated the two departments.	He combined the two departments.	\N	ADVANCED
584	その支払いを承認できますか。	Can you authorize the payment?	Can you approve the payment?	\N	ADVANCED
585	私たちは価格について交渉しました。	We negotiated the price yesterday.	We discussed the price yesterday.	\N	ADVANCED
586	彼は新人を指導しています。	He supervises the new employees.	He manages the new employees.	\N	ADVANCED
587	彼女は自分の考えをはっきり主張しました。	She asserted her opinion confidently.	She expressed her opinion confidently.	\N	ADVANCED
588	私はその結果を確認する必要があります。	I need to verify the result.	I need to confirm the result.	不定詞	ADVANCED
589	彼はその提案を正当化できませんでした。	He could not justify the proposal.	He could not explain the proposal well.	\N	ADVANCED
590	私はその政策を支持しています。	I strongly advocate the policy.	I strongly support the policy.	\N	ADVANCED
591	この薬は海外では禁止されています。	This medicine is prohibited overseas.	This medicine is banned overseas.	受動態	ADVANCED
592	政府は新しい規則を導入しました。	The government regulated the new industry.	The government introduced rules for the new industry.	\N	ADVANCED
593	その結果は私たちの予想と矛盾しています。	The result contradicts our expectation.	The result goes against our expectation.	\N	ADVANCED
594	この運動は痛みを和らげてくれます。	This exercise can alleviate the pain.	This exercise can reduce the pain.	\N	ADVANCED
595	その噂は彼の評判を弱めました。	The rumor undermined his reputation.	The rumor damaged his reputation.	\N	ADVANCED
596	最近その問題は少し改善しました。	The problem has diminished recently.	The problem has become smaller recently.	現在完了	ADVANCED
597	彼は最後まで生き残りました。	He managed to survive and ultimately prevail.	He managed to survive and finally win.	\N	ADVANCED
599	私は海外で多くの困難に直面しました。	I encountered many difficulties abroad.	I faced many difficulties abroad.	\N	ADVANCED
600	彼は成功を努力のおかげだと考えています。	He attributes his success to hard work.	He believes his success comes from hard work.	\N	ADVANCED
601	彼はその提案を拒否しました。	He rejected the proposal without hesitation.	He turned down the proposal without hesitation.	\N	ADVANCED
602	その薬は症状を抑えるのに役立ちます。	The medicine helps suppress the symptoms.	The medicine helps control the symptoms.	\N	ADVANCED
603	彼は緊張を隠そうとしました。	He attempted to conceal his nervousness.	He tried to hide his nervousness.	\N	ADVANCED
604	私たちは結果を予測できませんでした。	We could not anticipate the outcome.	We could not predict the outcome.	\N	ADVANCED
605	その説明は少し誇張されていました。	The explanation was slightly exaggerated.	The explanation was somewhat overstated.	受動態	ADVANCED
606	彼は新しい方針に反対しました。	He opposed the new policy.	He objected to the new policy.	\N	ADVANCED
607	私は彼の能力を過小評価していました。	I underestimated his ability.	I thought too little of his ability.	\N	ADVANCED
608	彼らは古い橋を修復しました。	They restored the old bridge.	They repaired the old bridge.	\N	ADVANCED
609	その地域は急速に発展しています。	The region is expanding rapidly.	The area is growing rapidly.	現在進行形	ADVANCED
610	私はその変化に気付いていませんでした。	I was unaware of the change.	I did not know about the change.	\N	ADVANCED
611	彼は危険性を誇張しました。	He exaggerated the risk.	He overstated the risk.	\N	ADVANCED
612	私たちは十分な証拠を集めました。	We gathered adequate evidence.	We gathered enough evidence.	\N	ADVANCED
613	彼女は自分の意見を控えました。	She restrained herself from commenting.	She held back from commenting.	動名詞	ADVANCED
614	私はその決定を支持し続けます。	I will continue to uphold the decision.	I will continue to support the decision.	\N	ADVANCED
615	その制度は多くの批判を受けています。	The system faces considerable criticism.	The system receives considerable criticism.	\N	ADVANCED
616	私たちは被害を最小限に抑えました。	We minimized the damage successfully.	We reduced the damage successfully.	\N	ADVANCED
617	その説明は完全には納得できません。	The explanation is not entirely convincing.	The explanation is not completely convincing.	\N	ADVANCED
618	彼は責任を引き受けることを拒みました。	He refused to assume responsibility.	He refused to take responsibility.	\N	ADVANCED
619	私は彼の態度に少し戸惑いました。	I was puzzled by his attitude.	I was confused by his attitude.	\N	ADVANCED
620	彼女は困難を克服しました。	She overcame the difficulty.	She got over the difficulty.	\N	ADVANCED
621	私たちは損失を避けることができました。	We managed to avoid substantial losses.	We managed to avoid major losses.	\N	ADVANCED
622	私は彼の説明を誤解していました。	I had misinterpreted his explanation.	I had misunderstood his explanation.	過去完了	ADVANCED
623	彼は事実を認めることを拒否しました。	He refused to acknowledge the fact.	He refused to admit the fact.	不定詞	ADVANCED
624	その契約は有効です。	The agreement remains valid.	The agreement is still valid.	\N	ADVANCED
625	私たちは計画を徐々に修正しました。	We gradually adjusted the strategy.	We gradually changed the strategy.	\N	ADVANCED
626	その情報はまだ公開されていません。	The information has not been disclosed yet.	The information has not been released yet.	現在完了	ADVANCED
627	彼は重要な事実を意図的に隠しました。	He deliberately withheld an important fact.	He deliberately kept an important fact from us.	\N	ADVANCED
628	私たちは非常時の計画を準備しました。	We prepared a contingency plan in advance.	We prepared an emergency plan in advance.	\N	ADVANCED
629	その説明は非常に説得力がありました。	His explanation was highly compelling.	His explanation was very persuasive.	\N	ADVANCED
630	彼は二週間連続で優勝しました。	He won the tournament for two consecutive weeks.	He won the tournament two weeks in a row.	\N	ADVANCED
631	私たちはその規則の適用を免除されました。	We were exempt from the rule.	We did not have to follow the rule.	受動態	ADVANCED
632	その変更は会社全体に影響しました。	The change predominantly affected our company.	The change mainly affected our company.	\N	ADVANCED
633	私はその遠回りを避けました。	I circumvented the heavy traffic.	I went around the heavy traffic.	\N	ADVANCED
634	彼はその情報を公表することを拒否しました。	He refused to disclose the information.	He refused to reveal the information.	不定詞	ADVANCED
635	私はその仕事を最後までやり遂げました。	I completed the assignment despite numerous obstacles.	I finished the assignment despite many obstacles.	\N	ADVANCED
636	彼は十分な証拠で自分の主張を裏付けました。	He substantiated his claim with solid evidence.	He supported his claim with solid evidence.	\N	ADVANCED
637	その二つの出来事は偶然同じ日に起こりました。	The two events coincided on the same day.	The two events happened on the same day.	\N	ADVANCED
638	私はその決定から除外されました。	I was excluded from the decision.	I was left out of the decision.	受動態	ADVANCED
639	その仕事は私から多くの時間を奪いました。	The task deprived me of my free time.	The task took away my free time.	\N	ADVANCED
640	彼はその状況を冷静に処理しました。	He handled the situation with composure.	He handled the situation calmly.	\N	ADVANCED
641	その問題は思ったより深刻でした。	The issue was more severe than expected.	The issue was more serious than expected.	比較	ADVANCED
642	私はその情報の信頼性を確認しました。	I verified the credibility of the information.	I confirmed the reliability of the information.	\N	ADVANCED
643	彼は契約条件を慎重に見直しました。	He examined the contract terms thoroughly.	He examined the contract carefully.	\N	ADVANCED
644	私たちはその方法を徐々に改善しました。	We gradually refined the method.	We gradually improved the method.	\N	ADVANCED
645	私は彼の成功を決して偶然だとは思いません。	I never regarded his success as mere coincidence.	I never thought his success was just luck.	\N	ADVANCED
646	彼は最後まで中立の立場を保ちました。	He remained neutral until the end.	He stayed neutral until the end.	\N	ADVANCED
647	その発表は大きな論争を引き起こしました。	The announcement caused considerable controversy.	The announcement caused major controversy.	\N	ADVANCED
648	私はその状況を客観的に説明しました。	I described the situation objectively.	I explained the situation objectively.	\N	ADVANCED
649	彼らは古い制度を徐々に廃止しました。	They gradually eliminated the old system.	They gradually removed the old system.	\N	ADVANCED
650	彼は困難な状況にもかかわらず冷静でした。	He remained composed despite the difficult situation.	He stayed calm despite the difficult situation.	\N	ADVANCED
651	彼は自分の立場を明確にしました。	He clarified his position immediately.	He made his position clear immediately.	\N	ADVANCED
652	私たちは結果を慎重に予測しました。	We estimated the outcome carefully.	We predicted the outcome carefully.	\N	ADVANCED
653	その建物は地震に耐えました。	The building withstood the earthquake.	The building survived the earthquake.	\N	ADVANCED
654	私は彼の成功を尊敬しています。	I admire his remarkable achievement.	I admire his impressive achievement.	\N	ADVANCED
655	彼は事実を故意にゆがめました。	He deliberately distorted the facts.	He intentionally twisted the facts.	\N	ADVANCED
656	その政策は経済を徐々に安定させました。	The policy gradually stabilized the economy.	The policy gradually made the economy stable.	\N	ADVANCED
657	私たちは危険を事前に察知しました。	We detected the danger in advance.	We noticed the danger in advance.	\N	ADVANCED
658	彼女は会議中ずっと集中していました。	She remained attentive throughout the meeting.	She stayed focused throughout the meeting.	\N	ADVANCED
659	私は彼の発言を引用しました。	I quoted his statement during the meeting.	I cited his statement during the meeting.	\N	ADVANCED
661	私たちは市場の変化を監視しています。	We monitor market changes closely.	We watch market changes closely.	現在進行形	ADVANCED
662	彼は最後まで冷静さを維持しました。	He maintained his composure until the end.	He stayed calm until the end.	\N	ADVANCED
663	私は彼の提案に異議を唱えました。	I challenged his proposal politely.	I questioned his proposal politely.	\N	ADVANCED
664	その決定は全員一致でした。	The decision was unanimous.	Everyone agreed with the decision.	受動態	ADVANCED
665	彼女は問題を論理的に説明しました。	She explained the issue logically.	She explained the issue in a logical way.	\N	ADVANCED
666	私は彼の説明を裏付ける証拠を見つけました。	I found evidence supporting his explanation.	I found evidence for his explanation.	分詞	ADVANCED
667	その制度は依然として有効です。	The system remains effective today.	The system is still effective today.	\N	ADVANCED
668	彼は状況にすぐ適応しました。	He adapted promptly to the situation.	He adapted quickly to the situation.	\N	ADVANCED
669	私たちは新しい基準を設定しました。	We established a new criterion.	We established a new standard.	\N	ADVANCED
670	その発表は予想以上の反響を呼びました。	The announcement generated unexpected attention.	The announcement received unexpected attention.	\N	ADVANCED
671	私はその証拠を慎重に分析しました。	I analyzed the evidence thoroughly.	I examined the evidence thoroughly.	\N	ADVANCED
672	彼は結果に影響を及ぼしました。	He influenced the outcome significantly.	He greatly influenced the outcome.	\N	ADVANCED
673	私たちは問題の優先順位を決めました。	We determined the priority of each issue.	We decided the priority of each issue.	\N	ADVANCED
674	その提案は現実に即しています。	The proposal is realistic and practical.	The proposal is realistic.	\N	ADVANCED
675	彼女は重要な役割を担いました。	She assumed a crucial role in the project.	She took an important role in the project.	\N	ADVANCED
676	私は彼の説明を裏付ける証拠を見つけました。	I found evidence that corroborated his explanation.	I found evidence supporting his explanation.	関係代名詞	ADVANCED
677	その雨で私たちの行動は制限されました。	The heavy rain constrained our movement.	The heavy rain limited our movement.	\N	ADVANCED
678	彼は自分の考えを明確に述べました。	He articulated his opinion clearly.	He expressed his opinion clearly.	\N	ADVANCED
679	その結果は以前の結論を無効にしました。	The result invalidated our previous conclusion.	The result made our previous conclusion invalid.	\N	ADVANCED
680	その発言は大きな議論を引き起こしました。	His comment provoked a heated discussion.	His comment started a heated discussion.	\N	ADVANCED
681	私たちは被害を最小限に抑えました。	We mitigated the damage successfully.	We reduced the damage successfully.	\N	ADVANCED
682	彼の説明は事実と食い違っていました。	His explanation deviated from the facts.	His explanation was different from the facts.	\N	ADVANCED
683	その出来事は大雨に先立って起こりました。	The incident preceded the heavy rain.	The incident happened before the heavy rain.	\N	ADVANCED
684	その後の調査で新たな事実が分かりました。	A subsequent investigation revealed new facts.	A later investigation revealed new facts.	\N	ADVANCED
685	私はその提案には十分な根拠があると思います。	I think the proposal has sufficient validity.	I think the proposal is well supported.	\N	ADVANCED
686	彼は最後まで冷静さを保ちました。	He remained composed throughout the meeting.	He stayed calm throughout the meeting.	\N	ADVANCED
687	私たちは重要な情報を省略しました。	We omitted the unnecessary details.	We left out the unnecessary details.	\N	ADVANCED
688	彼は会議への出席を辞退しました。	He declined to attend the meeting.	He refused to attend the meeting.	不定詞	ADVANCED
689	私はその情報の正確性を確認しました。	I verified the accuracy of the information.	I confirmed the accuracy of the information.	\N	ADVANCED
690	その会社は市場を支配しています。	The company dominates the local market.	The company leads the local market.	\N	ADVANCED
691	彼は状況を誤って判断しました。	He misjudged the situation completely.	He judged the situation incorrectly.	\N	ADVANCED
692	私たちはすべての可能性を考慮しました。	We considered every conceivable possibility.	We considered every possible situation.	\N	ADVANCED
693	彼は最後まで一貫した態度を保ちました。	He remained consistent until the end.	He stayed consistent until the end.	\N	ADVANCED
694	その提案は多くの批判を招きました。	The proposal attracted widespread criticism.	The proposal received widespread criticism.	\N	ADVANCED
695	私はその結論に異議があります。	I object to that conclusion.	I disagree with that conclusion.	\N	ADVANCED
696	彼女は重要な事実を強調しました。	She highlighted the important fact.	She emphasized the important fact.	\N	ADVANCED
697	その事故は完全に防ぐことができました。	The accident was entirely preventable.	The accident could have been completely prevented.	受動態	ADVANCED
698	彼は証拠不足で釈放されました。	He was released due to insufficient evidence.	He was released because there was not enough evidence.	受動態	ADVANCED
699	私はその出来事を鮮明に思い出せます。	I can vividly recall the event.	I can clearly remember the event.	\N	ADVANCED
700	その政策は多くの国で採用されています。	The policy has been adopted worldwide.	The policy has been adopted in many countries.	現在完了	ADVANCED
701	彼は新しい環境にすぐ順応しました。	He adapted swiftly to the new environment.	He adapted quickly to the new environment.	\N	ADVANCED
702	私はその契約の詳細を確認しました。	I examined the contract specifications carefully.	I examined the contract details carefully.	\N	ADVANCED
703	その会社は競争力を維持しています。	The company remains competitive in the global market.	The company stays competitive in the global market.	\N	ADVANCED
704	私たちは誤解を解消しました。	We resolved the misunderstanding peacefully.	We settled the misunderstanding peacefully.	\N	ADVANCED
705	彼は状況を客観的に認識していました。	He perceived the situation objectively.	He understood the situation objectively.	\N	ADVANCED
706	その決定は倫理的に問題があります。	The decision is ethically questionable.	The decision is ethically problematic.	\N	ADVANCED
707	私はその問題に直面したことがありません。	I have never encountered such an issue before.	I have never faced such an issue before.	現在完了	ADVANCED
708	その会社は海外企業を買収しました。	The company acquired an overseas business.	The company bought an overseas business.	\N	ADVANCED
709	彼は最後まで自分の立場を守りました。	He maintained his stance until the end.	He kept his position until the end.	\N	ADVANCED
710	私たちは結果を慎重に予測しました。	We projected the results cautiously.	We predicted the results cautiously.	\N	ADVANCED
711	その事故は完全に予防できたはずです。	The accident was entirely avoidable.	The accident could have been avoided completely.	受動態	ADVANCED
712	私はその資料を参考資料として利用しました。	I used the document as a reference.	I referred to the document.	\N	ADVANCED
713	その結果は予想と一致しました。	The outcome corresponded with our expectations.	The outcome matched our expectations.	\N	ADVANCED
714	彼は重要な役割を果たし続けています。	He continues to fulfill an important role.	He continues to play an important role.	現在進行形	ADVANCED
715	私たちは長期的な影響を考慮しました。	We considered the long-term implications.	We considered the long-term effects.	\N	ADVANCED
716	彼女はその申し出を丁寧に断りました。	She politely declined the offer.	She politely turned down the offer.	\N	ADVANCED
717	私は彼の説明に納得しました。	His explanation was convincing enough for me.	His explanation convinced me.	\N	ADVANCED
718	彼は会社の評判を守ろうとしました。	He tried to protect the company's reputation.	He tried to preserve the company's reputation.	不定詞	ADVANCED
719	私たちは資源を有効活用する必要があります。	We need to utilize our resources effectively.	We need to use our resources effectively.	不定詞	ADVANCED
720	彼女はその状況を冷静に受け入れました。	She accepted the situation gracefully.	She accepted the situation calmly.	\N	ADVANCED
721	その変更は業界全体に影響しました。	The revision affected the entire industry.	The change affected the entire industry.	\N	ADVANCED
722	私はその話が本当か疑っています。	I am skeptical about the story.	I doubt the story.	\N	ADVANCED
723	彼は新しい技術をすぐ習得しました。	He mastered the new technology quickly.	He learned the new technology quickly.	\N	ADVANCED
724	その製品は高い耐久性があります。	The product offers exceptional durability.	The product is extremely durable.	\N	ADVANCED
725	私たちは重要な課題を優先しました。	We prioritized the critical issues first.	We dealt with the most important issues first.	\N	ADVANCED
726	その証拠は彼の主張を裏付けています。	The evidence reinforces his claim.	The evidence strongly supports his claim.	\N	ADVANCED
727	私はその計画の実現性を疑っています。	I doubt the feasibility of the plan.	I doubt whether the plan is practical.	\N	ADVANCED
728	彼は批判をものともせず前へ進みました。	He proceeded despite the criticism.	He moved forward despite the criticism.	\N	ADVANCED
729	その発言は誤解を招く可能性があります。	The statement may be misleading.	The statement may cause misunderstanding.	\N	ADVANCED
731	彼女は最後まで冷静な判断を保ちました。	She maintained sound judgment until the end.	She kept good judgment until the end.	\N	ADVANCED
732	私はその数字の正確さを疑いません。	I do not question the accuracy of the figures.	I do not doubt the accuracy of the figures.	\N	ADVANCED
733	その提案は現実的とは言えません。	The proposal is hardly realistic.	The proposal is not very realistic.	\N	ADVANCED
734	彼は困難な状況を乗り切りました。	He overcame the adversity successfully.	He overcame the difficult situation successfully.	\N	ADVANCED
735	私たちは長年の対立を解決しました。	We resolved the long-standing conflict.	We settled the long-standing conflict.	\N	ADVANCED
736	私は彼の発言を文字どおり受け取りました。	I took his remark literally.	I understood his remark literally.	\N	ADVANCED
737	その研究は十分な信頼性があります。	The study has considerable credibility.	The study is highly reliable.	\N	ADVANCED
738	彼は新しい環境でも能力を発揮しました。	He demonstrated competence in the new environment.	He showed his ability in the new environment.	\N	ADVANCED
739	その計画は会社の方針と一致しています。	The plan is consistent with company policy.	The plan matches company policy.	\N	ADVANCED
740	私はその状況を誤って解釈していました。	I had misperceived the situation.	I had misunderstood the situation.	過去完了	ADVANCED
741	その決定は最終的に覆されました。	The decision was eventually reversed.	The decision was eventually changed.	受動態	ADVANCED
742	彼女は問題の本質を理解しています。	She understands the essence of the issue.	She understands the core of the issue.	\N	ADVANCED
743	私はその結果を冷静に受け止めました。	I accepted the outcome objectively.	I accepted the result calmly.	\N	ADVANCED
744	その制度は時代遅れになっています。	The system has become outdated.	The system has become old-fashioned.	現在完了	ADVANCED
745	彼は事実を意図的に無視しました。	He deliberately ignored the facts.	He intentionally ignored the facts.	\N	ADVANCED
746	私たちは状況を総合的に判断しました。	We assessed the situation comprehensively.	We evaluated the situation comprehensively.	\N	ADVANCED
747	彼女は仕事への意欲を失いませんでした。	She remained motivated at work.	She stayed motivated at work.	\N	ADVANCED
748	私はその提案の欠点を指摘しました。	I pointed out the proposal's shortcomings.	I pointed out the proposal's weaknesses.	\N	ADVANCED
749	その事故は深刻な結果を招きました。	The accident resulted in serious consequences.	The accident caused serious consequences.	\N	ADVANCED
750	私たちは新しい方針に徐々に適応しました。	We gradually adapted to the new policy.	We gradually got used to the new policy.	\N	ADVANCED
\.


--
-- TOC entry 3847 (class 0 OID 30532)
-- Dependencies: 222
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, user_id, password, role) FROM stdin;
1	mawsonlakes790913	$2a$10$LWER7/p5WN.byuyxE10LPewrDmL0HQJn0MFtUNd1JxQZXYhvoH/SG	ROLE_ADMIN
11	adelaide790913	$2a$10$jbVneiyjWpc7mzMS18Wj/uojMtfMw/zaGoqljTbkG5riwHn8ayUbO	ROLE_GENERAL
\.


--
-- TOC entry 3849 (class 0 OID 30635)
-- Dependencies: 224
-- Data for Name: favorites; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.favorites (user_id, question_id, created_at) FROM stdin;
1	102	2026-07-14 15:26:52.268512
1	103	2026-07-14 15:27:52.268512
1	104	2026-07-14 15:28:52.268512
1	105	2026-07-14 15:29:52.268512
1	106	2026-07-14 15:30:52.268512
1	107	2026-07-14 15:31:52.268512
1	108	2026-07-14 15:32:52.268512
1	109	2026-07-14 15:33:52.268512
1	110	2026-07-14 15:34:52.268512
1	111	2026-07-14 15:35:52.268512
1	112	2026-07-14 15:36:52.268512
1	113	2026-07-14 15:37:52.268512
1	114	2026-07-14 15:38:52.268512
1	115	2026-07-14 15:39:52.268512
1	116	2026-07-14 15:40:52.268512
1	117	2026-07-14 15:41:52.268512
1	118	2026-07-14 15:42:52.268512
1	119	2026-07-14 15:43:52.268512
1	120	2026-07-14 15:44:52.268512
1	121	2026-07-14 15:45:52.268512
1	122	2026-07-14 15:46:52.268512
1	123	2026-07-14 15:47:52.268512
1	124	2026-07-14 15:48:52.268512
1	125	2026-07-14 15:49:52.268512
1	126	2026-07-14 15:50:52.268512
1	127	2026-07-14 15:51:52.268512
1	128	2026-07-14 15:52:52.268512
1	129	2026-07-14 15:53:52.268512
1	130	2026-07-14 15:54:52.268512
1	131	2026-07-14 15:55:52.268512
1	132	2026-07-14 15:56:52.268512
1	133	2026-07-14 15:57:52.268512
1	134	2026-07-14 15:58:52.268512
1	135	2026-07-14 15:59:52.268512
1	136	2026-07-14 16:00:52.268512
1	137	2026-07-14 16:01:52.268512
1	138	2026-07-14 16:02:52.268512
1	139	2026-07-14 16:03:52.268512
1	140	2026-07-14 16:04:52.268512
1	141	2026-07-14 16:05:52.268512
1	142	2026-07-14 16:06:52.268512
1	143	2026-07-14 16:07:52.268512
1	144	2026-07-14 16:08:52.268512
1	145	2026-07-14 16:09:52.268512
1	146	2026-07-14 16:10:52.268512
1	147	2026-07-14 16:11:52.268512
1	148	2026-07-14 16:12:52.268512
1	149	2026-07-14 16:13:52.268512
1	150	2026-07-14 16:14:52.268512
1	151	2026-07-14 16:15:52.268512
1	152	2026-07-14 16:16:52.268512
1	153	2026-07-14 16:17:52.268512
1	154	2026-07-14 16:18:52.268512
1	155	2026-07-14 16:19:52.268512
1	156	2026-07-14 16:20:52.268512
1	157	2026-07-14 16:21:52.268512
1	158	2026-07-14 16:22:52.268512
1	159	2026-07-14 16:23:52.268512
1	160	2026-07-14 16:24:52.268512
1	161	2026-07-14 16:25:52.268512
1	162	2026-07-14 16:26:52.268512
1	163	2026-07-14 16:27:52.268512
1	164	2026-07-14 16:28:52.268512
1	165	2026-07-14 16:29:52.268512
1	166	2026-07-14 16:30:52.268512
1	167	2026-07-14 16:31:52.268512
1	168	2026-07-14 16:32:52.268512
1	169	2026-07-14 16:33:52.268512
1	170	2026-07-14 16:34:52.268512
1	171	2026-07-14 16:35:52.268512
1	172	2026-07-14 16:36:52.268512
1	173	2026-07-14 16:37:52.268512
1	174	2026-07-14 16:38:52.268512
1	175	2026-07-14 16:39:52.268512
1	176	2026-07-14 16:40:52.268512
1	177	2026-07-14 16:41:52.268512
1	178	2026-07-14 16:42:52.268512
1	179	2026-07-14 16:43:52.268512
1	180	2026-07-14 16:44:52.268512
1	181	2026-07-14 16:45:52.268512
1	182	2026-07-14 16:46:52.268512
1	183	2026-07-14 16:47:52.268512
1	184	2026-07-14 16:48:52.268512
1	185	2026-07-14 16:49:52.268512
1	186	2026-07-14 16:50:52.268512
1	187	2026-07-14 16:51:52.268512
1	188	2026-07-14 16:52:52.268512
1	189	2026-07-14 16:53:52.268512
1	190	2026-07-14 16:54:52.268512
1	191	2026-07-14 16:55:52.268512
1	192	2026-07-14 16:56:52.268512
1	193	2026-07-14 16:57:52.268512
1	194	2026-07-14 16:58:52.268512
1	195	2026-07-14 16:59:52.268512
1	196	2026-07-14 17:00:52.268512
1	197	2026-07-14 17:01:52.268512
1	198	2026-07-14 17:02:52.268512
1	199	2026-07-14 17:03:52.268512
1	200	2026-07-14 17:04:52.268512
1	201	2026-07-14 17:05:52.268512
1	202	2026-07-14 17:06:52.268512
1	203	2026-07-14 17:07:52.268512
1	204	2026-07-14 17:08:52.268512
1	205	2026-07-14 17:09:52.268512
1	206	2026-07-14 17:10:52.268512
1	207	2026-07-14 17:11:52.268512
1	208	2026-07-14 17:12:52.268512
1	209	2026-07-14 17:13:52.268512
1	210	2026-07-14 17:14:52.268512
1	211	2026-07-14 17:15:52.268512
1	212	2026-07-14 17:16:52.268512
1	213	2026-07-14 17:17:52.268512
1	214	2026-07-14 17:18:52.268512
1	215	2026-07-14 17:19:52.268512
1	216	2026-07-14 17:20:52.268512
1	217	2026-07-14 17:21:52.268512
1	218	2026-07-14 17:22:52.268512
1	219	2026-07-14 17:23:52.268512
1	220	2026-07-14 17:24:52.268512
1	221	2026-07-14 17:25:52.268512
1	222	2026-07-14 17:26:52.268512
1	223	2026-07-14 17:27:52.268512
1	224	2026-07-14 17:28:52.268512
1	225	2026-07-14 17:29:52.268512
1	226	2026-07-14 17:30:52.268512
1	227	2026-07-14 17:31:52.268512
1	228	2026-07-14 17:32:52.268512
1	229	2026-07-14 17:33:52.268512
1	230	2026-07-14 17:34:52.268512
1	231	2026-07-14 17:35:52.268512
1	232	2026-07-14 17:36:52.268512
1	233	2026-07-14 17:37:52.268512
1	234	2026-07-14 17:38:52.268512
1	235	2026-07-14 17:39:52.268512
1	236	2026-07-14 17:40:52.268512
1	237	2026-07-14 17:41:52.268512
1	238	2026-07-14 17:42:52.268512
1	239	2026-07-14 17:43:52.268512
1	240	2026-07-14 17:44:52.268512
1	241	2026-07-14 17:45:52.268512
1	242	2026-07-14 17:46:52.268512
1	243	2026-07-14 17:47:52.268512
1	244	2026-07-14 17:48:52.268512
1	245	2026-07-14 17:49:52.268512
1	246	2026-07-14 17:50:52.268512
1	247	2026-07-14 17:51:52.268512
1	248	2026-07-14 17:52:52.268512
1	101	2026-07-14 18:56:23.612428
1	337	2026-07-14 18:56:35.416602
1	659	2026-07-14 18:56:41.792635
1	707	2026-07-14 18:56:43.413904
1	624	2026-07-14 18:56:45.144358
1	286	2026-07-14 18:56:59.768265
1	2	2026-07-24 23:58:21.239207
1	7	2026-08-06 23:12:51.981277
\.


--
-- TOC entry 3848 (class 0 OID 30616)
-- Dependencies: 223
-- Data for Name: study_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.study_history (user_id, question_id, evaluation, evaluation_updated_at) FROM stdin;
1	659	EASY	2026-07-09 15:33:50.25667
1	707	GOOD	2026-07-09 15:33:51.774064
1	624	EASY	2026-07-09 15:33:52.584296
1	500	HARD	2026-07-15 20:06:14.340174
1	651	EASY	2026-07-16 02:05:08.251579
1	652	GOOD	2026-07-16 02:05:10.368679
1	653	HARD	2026-07-16 02:05:11.333338
1	337	GOOD	2026-07-16 02:05:45.028325
1	286	HARD	2026-07-16 02:05:47.212682
1	201	GOOD	2026-07-17 16:21:19.753391
1	102	EASY	2026-07-29 20:10:08.706233
1	103	EASY	2026-07-29 20:10:10.168233
1	104	EASY	2026-07-29 20:10:10.746653
1	105	EASY	2026-07-29 20:10:11.339534
1	106	EASY	2026-07-29 20:10:11.923948
1	101	EASY	2026-07-29 21:51:32.186676
1	1	EASY	2026-08-06 23:10:53.698566
1	2	GOOD	2026-08-06 23:10:54.746449
1	3	HARD	2026-08-06 23:10:55.831633
1	4	GOOD	2026-08-06 23:12:47.203257
1	5	EASY	2026-08-06 23:12:48.46459
1	6	HARD	2026-08-06 23:12:50.169838
1	7	GOOD	2026-08-06 23:12:52.744441
\.


--
-- TOC entry 3855 (class 0 OID 0)
-- Dependencies: 219
-- Name: question_question_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.question_question_id_seq', 758, true);


--
-- TOC entry 3856 (class 0 OID 0)
-- Dependencies: 221
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 11, true);


-- Completed on 2026-08-07 03:19:22 JST

--
-- PostgreSQL database dump complete
--

\unrestrict MDsJaXljfGs2symb8P0sLLixuRlErcIWZ4SAO6hk6POUOsjoK4kfTulItbTGJ3G

