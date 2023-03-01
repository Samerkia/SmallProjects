using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class GameOver : MonoBehaviour
{   
    public void onStart()
    {
        SceneManager.LoadScene("Dungeon1");
    }
    public void onQuit()
    {
        Application.Quit();
    }
}