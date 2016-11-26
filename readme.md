Not long ago, a spam campaign originated on some of the major social networks, and has started affecting Kik users as well. Most of the spam comes from a limited number of highly-motivated individuals, possibly from a single group, who constantly update their spam software. What started off as simple messages-sending bots now evolved into something that requires a large team of engineers to fight against it.

At the very beginning, the bots were not that clever. The spam detection could essentially be narrowed down to checking several simple criteria. For the given user's stream of messages over a given time period, the spammer could be identified if:

    more than 90 % of all messages had fewer than 5 words (here, a word is defined as a sequence of consecutive Latin letters which is neither preceded nor followed by a Latin letter);
    more than 50 % of messages to any one user had the same content, assuming that there were at least 2 messages to that user;
    more than 50 % of all messages had the same content, assuming that there were at least 2 messages;
    more than 50 % of all messages contained at least one of the words from the given list of spamSignals (the letters' case doesn't matter).

Since you are applying for the Anti-Spam team at Kik, you want to make sure you understand how the basic spam detection programs worked. Implement a function that, given a stream of messages and a list of spamSignals checks if it is possible that the user is a spammer by checking the criteria above.

Example

    For

    messages = [["Sale today!", "2837273"],
                ["Unique offer!", "3873827"],
                ["Only today and only for you!", "2837273"],
                ["Sale today!", "2837273"],
                ["Unique offer!", "3873827"]]

    and spamSignals = ["sale", "discount", "offer"], the output should be

    spamDetection(messages, spamSignals) = [
      "passed",
      "failed: 2837273 3873827",
      "passed",
      "failed: offer sale"
    ]

    Here are the results of the checks per criterion:
        4 out of 5 (80 %) messages have fewer than five words, which is fine;
        2 out of 3 messages to user 2837273 are the same, which is a good spam indicator; also, both messages to user 3873827 are the same;
        2 out of 5 (40 %) messages have the same content, which is fine;
        4 out of 5 (80 %) messages contain spam signals "offer" or "sale".

    For

    messages = [["Check Codefights out", "7284736"],
                ["Check Codefights out", "7462832"],
                ["Check Codefights out", "3625374"],
                ["Check Codefights out", "7264762"]]

    and spamSignals = ["sale", "discount", "offer"], the output should be

    spamDetection(messages, spamSignals) = [
      "failed: 1/1",
      "passed",
      "failed: Check Codefights out",
      "passed"
    ]

    Since all users in messages received only one message each, it's impossible to check the second criterion. The fourth criterion doesn't match: there are no spam signals in the messages. However, the first and the third criteria failed, since all the messages contain 4 words and have the same content.

Input/Output

    [time limit] 3000ms (java)

    [input] array.array.string messages

    Array of messages, where each message is given in the format [message, id of recipient].

    Constraints:
    1 = messages.length = 100,
    messages[i].length = 2,
    1 = messages[i][0].length = 100,
    1 = int(messages[i][1]) = 109.

    [input] array.string spamSignals

    Array of unique spam signals, where each spam signal consists of lowercase English letters.

    Constraints:
    1 = spamSignals.length = 30,
    1 = spamSignals[i].length = 25.

    [output] array.string

    Array of 4 strings, the results of checks per criterion. The results for each criterion should be given in the following format:
        "passed" if the check doesn't suggest that the user is a spammer, otherwise:
            for the first criterion: "failed: <failed_ratio>", where <failed_ratio> is the ratio of messages with fewer than 5 words as a reduced fraction;
            for the second criterion: "failed: <recipient_1> <recipient_2> ...", where <recipient_i> is id of the spammed user. Recipients should be sorted in ascending order of their ids;
            for the third criterion: "failed: <message>", where <message> is the message sent to more than 50 % of recipients;
            for the fourth criterion: "failed: <spamSignal_1> <spamSignal_2> ...", where <spamSignal_i> is the spam signal that appeared in at least one message. Spam signals should be sorted lexicographically.

