package com.febriaroosita.swt;

/**
 * Created by febria on 11/4/15.
 */
public class RemoveInflectionalParticle implements VisitorInterface
{
    //km cariapa ko //oh cuma ngecek algo e // kyk e sing paling ngefek waktu aku ambil kata dari editor trs tak masukin
    //ke pengecekan, itu dimana ya? oh disini, pas ngetik apa pas ngeload open?
    //disaat pengecekanmu jalan pokok e.. mau dari ngetik ae ngeload, yg manggil
    public void visit(CContext context)
    {
        String result = this.remove(context.currentWord.toLowerCase());

        if (result != context.currentWord && !result.equals("")) {
            String removedPart = context.currentWord.replaceAll(result, "");
            if (!removedPart.equals("")) {
                removalClass removal = new removalClass(
                        this,
                        context.currentWord,
                        result,
                        removedPart,
                        "P"
                );

                context.addRemoval(removal);
                context.currentWord = result;
            }
        }
    }

    /**
     * Remove inflectional particle : lah|kah|tah|pun
     *
     */
    public String remove(String word)
    {
        return word.replaceAll("-*(lah|kah|tah|pun)$", "");
    }
}

