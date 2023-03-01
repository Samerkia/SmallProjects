using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class GUIManager : MonoBehaviour
{
    [SerializeField]
    private TMP_InputField textInput;
    public void onButtonClick()
    {
        Debug.Log("Button was clicked");
        Debug.Log($"Input field is {textInput.text}");
    }
}
