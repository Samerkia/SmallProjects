using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class Pause : MonoBehaviour
{
    [SerializeField]
    private GameObject PauseButton;
    [SerializeField]
    private GameObject ResumeButton;
    [SerializeField]
    private GameObject RestartButton;
    [SerializeField]
    private GameObject MenuButton;
    public void onPause()
    {
        ResumeButton.SetActive(true);
        RestartButton.SetActive(true);
        MenuButton.SetActive(true);
    }
    public void onResume()
    {
        ResumeButton.SetActive(false);
        RestartButton.SetActive(false);
        MenuButton.SetActive(false);
    }
    public void onRestart()
    {
        SceneManager.LoadScene("Dungeon1");
    }
    public void onMenu()
    {
        SceneManager.LoadScene("Start");
    }
}