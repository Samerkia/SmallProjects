using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using UnityEngine.InputSystem;
using TMPro;

public class Inventory : MonoBehaviour
{
    [SerializeField]
    private int KeyCount = 0;
    [SerializeField]
    private TextMeshProUGUI invGUI;
    private float MAX_HEALTH = 100f;
    [SerializeField]
    private float Current_HEALTH = 100f;
    [SerializeField]
    private Image bar;
    // Start is called before the first frame update
    void Start()
    {

    }

    public void setKeyCount(int count)
    {
        KeyCount = count;
        invGUI.text = "Keys: " + getKeyCount();
    }
    public int getKeyCount()
    {
        return KeyCount;
    }

    public void setHealth(float newhealth)
    {
        Current_HEALTH = newhealth;
        if (Current_HEALTH > MAX_HEALTH)
        {
            Current_HEALTH = MAX_HEALTH;
        }
        if (Current_HEALTH <= 0)
        {
            Current_HEALTH = 0;
            SceneManager.LoadScene("Death");
        }

        bar.fillAmount = getHealth() / MAX_HEALTH;
    }
    public float getHealth()
    {
        return Current_HEALTH;
    }
}
