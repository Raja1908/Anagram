/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList;
    private HashSet<String> wordset;
    private HashMap<String,ArrayList<String > > letterstoWord;
    private HashMap<Integer,ArrayList<String> > sizeToWords=new HashMap<Integer, ArrayList<String>>();
int i11=DEFAULT_WORD_LENGTH;
    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        Log.e("AnagramDictionary.java","Inside Constructor");
        String line;
        wordList=new ArrayList<String>();
        letterstoWord=new HashMap<String, ArrayList<String>>();
        wordset=new HashSet<String>();
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordset.add(word);
            String sort=sortLetters(word);


            if(letterstoWord.containsKey(sort))
            {
             letterstoWord.get(sort).add(word);
             //sizeToWords.get(sort.length()).add(word);
            }
            else
            {
                ArrayList<String> res =new ArrayList<String>();
                res.add(word);
                letterstoWord.put(sort,res);

                //sizeToWords.get(sort.length()).add(word);
            }
            if(sizeToWords.containsKey(sort.length()))
            {
                sizeToWords.get(sort.length()).add(word);
            }
            else
            {
                ArrayList< String> s=new ArrayList<>();

                s.add(word);
                sizeToWords.put(word.length(),s);
            }
        }

    }
    public  String sortLetters(String word)
    {
        char c[]=word.toCharArray();
        Arrays.sort(c);
        return new String(c);

    }

    public boolean isGoodWord(String word, String base) {
        if(wordset.contains(word) && !(word.toLowerCase()).contains(base.toLowerCase()))
        {
            return true;
        }
        return false;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        //ArrayList<String > res=new ArrayList<String >();

        if(letterstoWord.containsKey(sortLetters(targetWord)))
        {
            result=letterstoWord.get(sortLetters(targetWord));
        }
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        String alpha="abcdefghijklmnopqrstuvwxyz";
        for(int i=0;i<26;i++)
        {
           String word1=word+alpha.charAt(i);
          // if(isGoodWord(word1,word))
              ArrayList<String > dfg=new ArrayList<String>();
              dfg.addAll(getAnagrams(word1));
              Iterator it=dfg.iterator();
            while (it.hasNext())
            {
                String words= (String) it.next();
                if(isGoodWord((String) words,word))
                    result.add((String) words);
            }

        }
        return result;
    }

    public String pickGoodStarterWord() {
        List <String > l=sizeToWords.get(i11);
        i11++;
        if(i11>7)
            i11=4;
        int j=(int)(Math.floor(Math.random()*l.size()));
        for (int c=j;c<l.size();c++)
        {
            ArrayList<String> b=(ArrayList<String>)getAnagrams(l.get(c));
            if(b.size()>3)
                return l.get(c);}
            for (int c=0;c<j;c++)
            {
                ArrayList<String> b=(ArrayList<String>)getAnagrams(l.get(c));
                if (b.size()>=3)
                    return l.get(c);
            }
        i11++;
        return "run";

    }
}
